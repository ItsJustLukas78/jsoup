/*
 * Problem 2 Sell My Pet Food
 *
 * V1.0
 * 6/1/2019
 * Copyright(c) 2019 PLTW to present. All rights reserved
 */
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.io.*;

/**
 * A DataCollector class to manage social media posts
 */
public class DataCollector
{
    private ArrayList<Review> reviews;
    private ArrayList<String> targetWords;
    private HashMap<String, Double> wordsToWeights;
    private Scanner sc;
    private int currentPost;

    public DataCollector()
    {
        reviews = new ArrayList<Review>();
        targetWords = new ArrayList<String>();
        wordsToWeights = new HashMap<String, Double>();
        currentPost = 0;
    }

    /**
     * Gather the data contained in the files reviewsFilename and
     * targetWordsFilename (including punctuation), with words separated by a single
     * space
     *
     * @param reviewsFilename the name of the file containing social media posts
     * @param targetWordsFilename the name of the file containing the target words
     */
    public void setData(String reviewsFilename, String targetWordsFilename) {
        // read in the social media posts found in reviews
        // a try is like an if statement, "throwing" an error if the body of the try fails
        try
        {
//            sc = new Scanner(new File(reviewsFilename));
//            while (sc.hasNextLine())
//            {
//                // String method trim removes whitespace before and after a string
//                String temp = sc.nextLine().trim();
//                // DEBUG: System.out.println(temp);
//                this.reviews.add(temp);
//            }

            // Read the reviews from the file and store them in the reviews array
            sc = new Scanner(new File(reviewsFilename));

            // skip the first line
            sc.nextLine();

            while (sc.hasNextLine())
            {
                // String method trim removes whitespace before and after a string
                String temp = sc.nextLine().trim();
                String[] tempArray = temp.split(",");
                this.reviews.add(new Review(tempArray[2], tempArray[3], tempArray[4], tempArray[5].equals("true"), tempArray[0], tempArray[1]));
            }
            
            
        } catch (Exception e) { System.out.println("Error reading or parsing" + reviews + "\n" + e); }

        // read in the target words in targetWords and store their weights in wordsToWeights
        try
        {
            sc = new Scanner(new File(targetWordsFilename));

            // skip the first line
            sc.nextLine();

            while (sc.hasNextLine())
            {
                // String method trim removes whitespace before and after a string
                String temp = sc.nextLine().trim();
                String[] tempArray = temp.split(",");
                this.wordsToWeights.put(tempArray[0], Double.parseDouble(tempArray[1]));
                this.targetWords.add(tempArray[0]);
            }
        } catch (Exception e) { System.out.println("Error reading or parsing" + targetWords + "\n" + e); }
    }

    /**
     * Get the next post in reviews with words separated by a single space,
     * or "NONE" if there is no more data.
     *
     * @return a string containing one of the lines in reviews
     */
    public Review getNextReview()
    {
        if (currentPost < reviews.size())
        {
            this.currentPost++;
            return reviews.get(currentPost - 1);
        }
        else
        {
            return null;
        }
    }

//    /**
//     * Get the next line in targetWords, with words separated by a space,
//     * or "NONE" if there is no more data.
//     *
//     * @return a string containing one of the lines in targetWords
//     */
//    public String getNextTargetWord()
//    {
//        if (currentTargetWord < targetWords.size())
//        {
//            this.currentTargetWord++;
//            return targetWords.get(currentTargetWord - 1);
//        }
//        else
//        {
//            this.currentTargetWord = 0;
//            return "NONE";
//        }
//    }

    public ArrayList<String> getTargetWords()
    {
        return targetWords;
    }

    public double getWeight(String word)
    {
        return wordsToWeights.get(word);
    }

    public void createTargetMarketFile(String filename, ArrayList<Review> reviews)
    {
        try
        {
            // sort reviews by their weight
            reviews.sort((a, b) -> Double.compare(b.getWeight(), a.getWeight()));

            FileWriter fw = new FileWriter(filename);
            for (Review review : reviews) {
                fw.write(review.getUsername() + "\n");
            }
            fw.close();
        }
        catch (IOException e)
        {
            System.out.println("Could not write to file. " + e);
        }
    }

    public void prepareAdvertisement(String filename, ArrayList<Review> reviews, String advertisement)
    {
        try
        {
            // sort reviews by their weight
            reviews.sort((a, b) -> Double.compare(b.getWeight(), a.getWeight()));

            FileWriter fw = new FileWriter(filename);
            // Strin method split splits a string based on the provided token
            // and returns an array of individual substrings
//            for (String un : usernames.split(" "))
//            {
//                fw.write("@" + un + " " + advertisement +"\n");
//            }
//            fw.close();

            for (Review review : reviews)
            {
                fw.write("@" + review.getUsername() + ";" + advertisement + ";" + "Target weight: " + review.getWeight() + ";" + "Target words: " + review.getMatchedTargetWords() + "\n");
            }
            fw.close();
        }
        catch (IOException e)
        {
            System.out.println("Could not write to file. " + e);
        }
    }

    // Print out advertisements in a structured format to sample emails
    public void printAdvertisements()
    {
        try {
            sc = new Scanner(new File("targetedAd.csv"));
            while (sc.hasNextLine())
            {
                String temp = sc.nextLine().trim();
                String[] tempArray = temp.split(";");
                System.out.println("To: " + tempArray[0] + "@gmail.com");
                System.out.println("Subject: " + "Targeted Advertisement");
                System.out.println("Body: " + "Dear " + tempArray[0] + ",\n" + tempArray[1] + "\n" + tempArray[2] + "\n" + tempArray[3] + "\n");
            }
        } catch (Exception e) { System.out.println("Error reading or parsing" + targetWords + "\n" + e);
        }
    }

    /**
     * Print the array of posts
     */
    public void printAllReviews()
    {
        for (Review review : this.reviews)
            System.out.println(review);
    }

    /**
     * Print the array of target words
     */
    public void printAllTargetWords()
    {
        for (String word : this.targetWords)
            System.out.println(word);
    }
}