package com.abworks.structures.notifications;

import java.util.Scanner;

public class Driver {

    static String sep = ";";
    INotifier notifier;
    interface Command {
        void execute(String command);
    }
    class UserCommand implements Command{
        @Override
        public void execute(String command) {
            String username = command;
            notifier.createUser(username);
        }
    }
    class TopicCommand implements Command {

        @Override
        public void execute(String command) {
            String topicName = command;
            notifier.createTopic(topicName);
        }
    }
    static class ExitCommand implements Command {

        @Override
        public void execute(String command) {
            System.exit(0);
        }
    }
    static class NoopCommand implements Command {

        @Override
        public void execute(String command) {

        }
    }

    public void runDriver(){
        Scanner s = new Scanner(System.in);
        notifier = new NotificationsManager();
        while (true){
            System.out.printf("\n\n Enter command: ");
            String commandStr = s.nextLine();
            String[] parts = commandStr.split(sep);
            String commType = parts[0];
            Command command = switch (commType) {
                case "user" -> new UserCommand();
                case "topic" ->
                    new TopicCommand();
                case "exit" -> new ExitCommand();
                default -> new NoopCommand();
            };
            String subcommand = parts.length>1?parts[1]:"";
            command.execute(subcommand);
        }
    }

    public static void main(String[] args) {

        Driver d = new Driver();
        d.runDriver();
    }
}
