package com.example.oop_lab_3_2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private int a, b, c;
    private final List<ModelListener> listeners = new ArrayList<>();

    public Model() { loadNumbers(); }

    public void addListener(ModelListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(ModelListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners() {
        for (ModelListener listener : listeners) {
            listener.onModelChanged();
        }
    }

    public int getA() { return a; }
    public int getB() { return b; }
    public int getC() { return c; }

    public void setA(int a) {
        if (a < 0 || a > 100) return;
        if (this.a == a) return;
        this.a = a;
        changeByA();
        notifyListeners();
    }
    public void setB(int b) {
        if (b < 0 || b > 100) return;
        int oldB = this.b;
        if (b > c) b = c;
        if (b < a) b = a;
        this.b = b;
        if (oldB != b) notifyListeners();
    }
    public void setC(int c) {
        if (c < 0 || c > 100) return;
        if (this.c == c) return;
        this.c = c;
        changeByC();
        notifyListeners();
    }

    private void changeByA() {
        if (a > b) b = a;
        if (b > c) c = b;
    }
    private void changeByC() {
        if (c < b) b = c;
        if (b < a) a = b;
    }

    public void saveNumbers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("savedNumbers.txt"))) {
            writer.write(a + " " + b + " " + c);
        } catch (IOException ignored) {}
    }

    public void loadNumbers() {
        File file = new File("savedNumbers.txt");
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String[] parts = reader.readLine().split(" ");
            a = ogran(Integer.parseInt(parts[0]));
            b = ogran(Integer.parseInt(parts[1]));
            c = ogran(Integer.parseInt(parts[2]));
        } catch (IOException ignored) {}
        if (b < a) b = a;if (b > c) b = c;if (a > c) a = c;if (a > b) b = a;
    }

    private int ogran(int value) {
        return Math.min(Math.max(value, 0), 100);
    }
}
