package com.abworks.structures.virtualfilesystem;

import java.util.Map;

public class FSDriver {
    public static void main(String[] args) {
        Filesystem fs = new Filesystem();
        fs.mkdir("/a/dir1");
        fs.addOrUpdateFile("/a/k/myfile.txt", "new content");
        fs.mkdir("/a/dir2");
        fs.mkdir("/a/dir3");
        fs.mkdir("/a/dir4");
        fs.tree("/");
        fs.addOrUpdateFile("/a/dir4/dirmyfile.txt", "new content");
        Map<String, Object> params = Map.of("minsize", 1, "namepattern", "dir.*");

        fs.search(params);





    }
}
