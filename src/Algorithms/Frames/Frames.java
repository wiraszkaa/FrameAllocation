package Algorithms.Frames;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Frames {
    public int size;
    int currentIndex;
    HashMap<Integer, Integer> framesMap;
    public int[] framesArray;

    public Frames(int size) {
        this.size = size;

        framesMap = new HashMap<>();
        framesArray = new int[size];
        Arrays.fill(framesArray, -1);
    }

    public void increaseSize() {
        increaseSize(1);
    }

    public void increaseSize(int amount) {
        size += amount;
        handleIncreasedSize();
    }

    private void handleIncreasedSize() {
        if(size > framesArray.length) {
            int[] newArray = new int[size + 4];
            Arrays.fill(newArray, -1);

            System.arraycopy(framesArray, 0, newArray, 0, framesArray.length);
            framesArray = newArray;
        }
    }

    public void setSize(int amount) {
        if (amount > size) {
            increaseSize(amount - size);
        } else {
            decreaseSize(amount - size);
        }
    }

    public void decreaseSize() {
        if (currentIndex == size) {
            removeReference();
        }
        size--;
    }

    public void decreaseSize(int amount) {
        for(int i = 0; i < amount; i++) {
            if(size > 0) {
                decreaseSize();
            } else {
                break;
            }
        }
    }

    private void removeReference() {
        if(currentIndex > 0) {
            framesMap.remove(framesArray[currentIndex - 1]);
            framesArray[currentIndex - 1] = -1;
            currentIndex--;
        }
    }

    public boolean contains(int reference) {
        return framesMap.containsKey(reference);
    }

    public boolean add(int reference) {
        if (currentIndex == size) {
            return false;
        }
        framesMap.put(reference, currentIndex);
        framesArray[currentIndex] = reference;
        currentIndex++;
        return true;
    }

    public void swap(int currReference, int newReference) {
        framesMap.put(newReference, framesMap.get(currReference));
        framesArray[framesMap.get(currReference)] = newReference;
        framesMap.remove(currReference);
    }

    public int[] randSwap(int newReference) {
        Random rd = new Random();
        int index = rd.nextInt(size);
        int currReference = framesArray[index];
        swap(currReference, newReference);

        return new int[]{currReference, newReference};
    }

    public int[] optSwap(int[] array, int newReference) {
        int reference = framesArray[0];
        int duration = -1;
        for (int i : framesArray) {
            int temp = 0;
            for (int j : array) {
                if (i == j) {
                    break;
                }
                temp++;
            }
            if (temp > duration) {
                reference = i;
                duration = temp;
            }
        }
        swap(reference, newReference);

        return new int[]{reference, newReference};
    }
}
