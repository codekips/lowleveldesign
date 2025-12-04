package com.abworks.structures.virtualfilesystem;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;

@Getter
abstract class FSNode {
    protected String name;
    protected LocalDateTime createdAt;
    public FSNode(String name){
        this.name = name;
        this.createdAt = LocalDateTime.now();
    }
}

interface NodeFilter {
    boolean apply(FSNode node, Map<String, Object> params);
}
class SizeFilter implements NodeFilter{

    @Override
    public boolean apply(FSNode node, Map<String, Object> params) {
        if (node instanceof DirNode) return true;
        FileNode fileNode = (FileNode) node;
        boolean success = true;
        if (params.containsKey("minsize")){
            success &= fileNode.getSize() >= (Integer)params.get("minsize");
        }
        if (params.containsKey("maxsize")){
            success &= fileNode.getSize() <= (Integer)params.get("maxsize");
        }

        return success;
    }
}
class NameFilter implements NodeFilter {

    @Override
    public boolean apply(FSNode node, Map<String, Object> params) {
        if (!params.containsKey("namepattern")) return true;
        String exp = (String) params.get("namepattern");
        return Pattern.matches(exp, node.name);
    }
}
class NodeFilterChain {
    List<NodeFilter> filters;
    public NodeFilterChain(){
        filters = new ArrayList<>();
    }
    public void add(NodeFilter filter){
        filters.add(filter);
    }
    public boolean apply(FSNode node, Map<String, Object> params){
        for (NodeFilter filter: filters){
            if (!filter.apply(node, params))
                return false;
        }
        return true;
    }
}

class SearchService {

    NodeFilterChain filterChain;
    public SearchService(){
        filterChain = new NodeFilterChain();
        filterChain.add(new SizeFilter());
        filterChain.add(new NameFilter());
    }
    public List<FSNode> search(DirNode source, Map<String, Object> params) {
        if (source == null)
            throw new IllegalArgumentException("Directory source can not be null");
        List<FSNode> matched = searchRecursive(source, params);
        return matched;
    }

    private List<FSNode> searchRecursive(DirNode source, Map<String, Object> params) {
        List<FSNode> matches = new ArrayList<>();
        for (FSNode fsNode: source.getChildren().values()){
            if (filterChain.apply(fsNode, params))
                matches.add(fsNode);
            if (fsNode instanceof DirNode)
                matches.addAll(searchRecursive((DirNode) fsNode, params));
        }
        return matches;
    }
}

@Getter
class DirNode extends FSNode{
    private final TreeMap<String, FSNode> children;
    public DirNode(String name) {
        super(name);
        this.children = new TreeMap<>();
    }
    public void add(FSNode node){
        children.put(node.getName(), node);
    }
    public Optional<FSNode> getChild(String name){
        return Optional.ofNullable(children.get(name));
    }

    public FileNode addFile(String filename) {
        FileNode fileNode = new FileNode(filename);
        this.add(fileNode);
        return fileNode;
    }
}
class FileNode extends FSNode{
    private final StringBuilder contentBuilder = new StringBuilder();
    @Getter
    private int size = 0;


    public FileNode(String name) {
        super(name);
    }
    public void addContent(String newContent){
        contentBuilder.append(newContent);
        this.size += newContent.length();
    }
    public String read(){
        return contentBuilder.toString();
    }
}

public class Filesystem {
    static String fileSep = "/";
    private final DirNode root;
    private final SearchService searchService;
    public Filesystem(){
        this.root = new DirNode("/");
        this.searchService = new SearchService();
    }
    public void mkdir(String path){
        traverse(path, true);
    }
    public void addOrUpdateFile(String path, String content){
        int lastSeparator = path.lastIndexOf("/");
        String parentDirPath = path.substring(0, lastSeparator);
        String filename = path.substring(lastSeparator+1);
        DirNode parentDir = traverse(parentDirPath, true);
        FileNode fileNode = (FileNode) parentDir.getChildren().get(filename);
        if (fileNode == null){
            fileNode = parentDir.addFile(filename);
        }
        fileNode.addContent(content);
    }

    public void tree(String path){
        DirNode source = traverse(path, false);
        printRecursive(source, "");
    }
    public void search(Map<String, Object> params) {
        search("/", params);
    }

    public void search(String sourcePath, Map<String, Object> params){
        DirNode source = traverse(sourcePath, false);
        List<FSNode> matches = searchService.search(source, params);
        for (FSNode match: matches)
            System.out.println(match.name);

    }


    private void printRecursive(DirNode source, String indent) {
        System.out.println(indent + fileSep+ source.name);
        String newIndent = indent + "\t";
        for (FSNode child: source.getChildren().values()){
            if (child instanceof FileNode)
                System.out.println(newIndent + child.name);
            else
                printRecursive((DirNode) child, newIndent);
        }
    }

    private DirNode traverse(String path, boolean createMissingDirs) {
        String[] dirsInOrder = path.split(fileSep);
        DirNode curr = root;
        for (int i=1; i<dirsInOrder.length; i++){
            String dirName = dirsInOrder[i];
            Optional<FSNode> optNode = curr.getChild(dirName);
            if (optNode.isEmpty()){
                if (createMissingDirs){
                    curr.add(new DirNode(dirName));
                }
                else throw new IllegalArgumentException("Bad path :" + path);
            }
            curr = (DirNode) curr.getChild(dirName).get();
        }
        return curr;
    }
}
