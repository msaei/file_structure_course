package com.company;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.File;

public class Graph {
    ArrayList<Node> vertices;

    Graph() {
        vertices = new ArrayList<Node>();
    }

    Graph(File file){
        vertices = new ArrayList<Node>();
        try {
            Scanner in = new Scanner(file);
            int n = Integer.parseInt(in.nextLine()) ;
            for( int i=0 ; i < n; i++) this.vertices.add(new Node(i + 1 + "", in.nextLine()));
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void addVertex(Node node){
        this.vertices.add(node);
    }

    public void unVisitAll(){
        for (Node node: this.vertices) node.unvisit();
    }

    public void BFS(int n){
        Queue<Node> q = new LinkedList<Node>();
        Node v = this.vertices.get(n-1);
        v.visit();
        System.out.print(v.name + " ");
        q.add(v);
        while (q.size() != 0){
            v = q.remove();
            for (int w: v.neighbors){
                Node neighbor = this.vertices.get(w-1);
                if (!neighbor.visited) {
                    neighbor.visit();
                    System.out.print(neighbor.name + " ");
                    q.add(neighbor);
                }
            }
        }
        System.out.println();
    }

    public void DFS(int n){
        Node v = this.vertices.get(n-1);
        v.visit();
        System.out.print( v.name + " ");
        for (int w: v.neighbors) {
            Node neighbor = this.vertices.get(w-1);
            if (!neighbor.visited) DFS(w);
        }
    }

    public String toString(){
        String result = "";
        for (Node node : this.vertices) result += node + "\n";
        return result;
    }

}
