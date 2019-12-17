package com.company;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.io.File;
import java.util.*;


public class Main {
    static Graph graph;

    private static void createGraph() {
        String ans = JOptionPane.showInputDialog("Enter 'i' for Interactive or 'f' for File.");
        if (ans.charAt(0) == 'i' ){
            graph = new Graph();
            int vn = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of vertices"));
            for (int i = 1; i <= vn; i++){
                String vlist = JOptionPane.showInputDialog("Enter the list of neihbors for vertex " + i);
                graph.vertices.add(new Node( "" + i, vlist));
            }
            System.out.println(graph);

        } else {
            File selectedFile = new File("");
            JFileChooser fileChooser = new JFileChooser(".");
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) selectedFile = fileChooser.getSelectedFile();
            graph = new Graph(selectedFile);
            System.out.println(graph);
        }
    }

    public static void main(String[] args) {


        createGraph();

        char opt;
        int n;
        Scanner sin = new Scanner(System.in);
        do {
            System.out.println("Enter 'n' to create new graph:");
            System.out.println("Enter 'b' for BFS:");
            System.out.println("Enter 'd' for DFS:");
            System.out.println("Enter 'q' for Quit:");
            opt =sin.next().charAt(0);
            if (opt == 'n') createGraph();
            if (opt=='b'){
                System.out.println("Enter starting node for BFS:");
                n = sin.nextInt();
                graph.BFS(n);
                graph.unVisitAll(); //make all vertices unvisited
            }
            if (opt=='d'){
                System.out.println("Enter starting node for DFS:");
                n = sin.nextInt();
                graph.DFS(n);
                graph.unVisitAll();
                System.out.println();
            }
        } while ( opt != 'q' );


    }

}
