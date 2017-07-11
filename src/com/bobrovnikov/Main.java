package com.bobrovnikov;

import com.sun.deploy.util.StringUtils;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

    private static char[] ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    public static void main(String[] args) {
	    String testPath = "C:/Java/test";
	    File f = new File(testPath);

	    if (f.isDirectory()) {
            System.out.println("testPath exists..");
        } else {
	        System.out.println("testPath does not exist!");
        }

        // @todo: change args processing to handle tests
        if (args.length == 0) {
	        System.out.println("no args supplied");
	        System.exit(1);
        } else {
	        // @todo: validate chosen password (only lower/upper? latin)
	        System.out.println(String.format("`%s` is the chosen password", args[0]));
        }

        ArrayList<String> secretChars = new ArrayList<>(args[0].length());

	    for (int i = 0; i < args[0].length(); i++) {
	        secretChars.add(String.valueOf(args[0].charAt(i)));
        }

	    String fullPath = String.format("%s/%s", testPath, StringUtils.join(secretChars, "/"));

	    System.out.println("Trying to create " + fullPath);

	    String[] fullPathParts = fullPath.split("/");

	    String currentCheckChain = "";
	    File dir;

	    for (String node : fullPathParts) {
            currentCheckChain += node + "/";
            System.out.print(String.format("Checking %s .. ", currentCheckChain));

            dir = new File(currentCheckChain);
            if (dir.isDirectory()) {
                System.out.println("ok");
            } else {
                // @todo: create all alphabet dirs
                if (dir.mkdir()) {
                    System.out.println("created");
                } else {
                    System.out.println("failed to create");
                    System.exit(1);
                }
            }

            System.out.println(new File(currentCheckChain).isDirectory() ? "ok" : "no");

            System.out.print("Creating whole alphabet dirs.. ");
            for (char alphabetChar : ALPHABET) {
                dir = new File(currentCheckChain + alphabetChar);
                if (!dir.isDirectory()) {
                    dir.mkdir();
                }
            }
            System.out.println("ok");
        }

        try {
            Desktop.getDesktop().open(new File(fullPath));
        } catch (IOException e) {
            System.out.println("Could not open target folder");
        }
    }
}
