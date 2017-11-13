package com.ofn.dao.impl;

public class DataManipulator {

    public static String[] varArgs(String[] args, int arrLength) {
        String[] allArgs = new String[arrLength];
        System.arraycopy(args, 0, allArgs, 0, args.length);
        for (int i = args.length; i < allArgs.length; i++) {
            allArgs[i] = null;
        }
        return allArgs;
    }

    public static String preparify(String s){
        if (s == null || s.isEmpty()) {
            s = null;
        }
        return  s;
    }

    public static String searchify(String s){
        if (s == null || s.isEmpty()) return null;
        else return "%" + s + "%";
    }

}
