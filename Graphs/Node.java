package com.company;

import java.util.ArrayList;

public class Node {
    String name;
    ArrayList<Integer> neighbors = new ArrayList<Integer>();
    boolean visited = false;

    Node(String name, String line) {
        this.name = name;
        String[] nebs = line.split(" ");
        for (String neb : nebs) this.neighbors.add(Integer.parseInt(neb));
    }
    public void visit(){
        this.visited = true;
    }
    public void unvisit(){
        this.visited = false;
    }

    public String toString(){
        return this.name + "->" + this.neighbors;
    }
}
