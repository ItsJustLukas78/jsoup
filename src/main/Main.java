// import jsoup then use it to scrape google.com html page

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

class Main {

  private static String url = "https://www.amazon.com/Yonex-Mavis-Nylon-Badminton-Shuttlecock/product-reviews/B01CB0VZM8/ref=cm_cr_dp_d_show_all_btm?ie=UTF8&reviewerType=all_reviews";
  private static String targetWordsFile = "targetWords.csv";

  public static void main(String[] args) {
    System.out.println("Hello, welcome to AdScout! You can use this program to scrape reviews and prepare targeted ads for your product.");
    System.out.println("Before using this program, create a csv file with your target words and their weights, and find a amazon page with reviews that may be relevant to your product.");
    System.out.println("Once you have done that, please input the url of the webpage you would like to scrape, or press enter to use the default url.");

    Scanner scanner = new Scanner(System.in);
    String input = scanner.nextLine().trim();
    if (input.equals("exit")) {
      System.exit(0);
    } else if (!(input.equals("\r") || input.equals(""))) {
      url = input;
    }

    System.out.println("Now, please enter the name of the csv file containing your target words and weights or press enter to use the default file.");

    input = scanner.nextLine().trim();
    if (input.equals("exit")) {
      System.exit(0);
    } else {
      if (input.equals("\r") || input.equals("")) {
        input = targetWordsFile;
      } else {
        targetWordsFile = input;
      }
    }

    scrapeWebpageAndStoreReviews();

    System.out.println("The reviews have been scraped and stored in a csv file called reviews.csv. Please type out your advertisement or press enter to use the default advertisement.");

    String advertisement = scanner.nextLine().trim();
    if (advertisement.equals("exit")) {
      System.exit(0);
    } else {
      TargetedAd.createTargetedAd(advertisement, targetWordsFile);
    }

    System.out.println("The targeted ad has been created and stored in a csv file called targetedAd.csv. Thank you for using AdScout!");


  }


  public static void scrapeWebpageAndStoreReviews() {
    try {
      WebDriver driver = new ChromeDriver();
      driver.get(url);

      ArrayList<Review> reviews = new ArrayList<Review>();

      // Amazon tends to utilize captcha puzzles, so we need to wait for the user to solve it before capturing the page source
      System.out.println("Once the page has loaded, press enter to continue");
      System.in.read();

      while (true) {
        String pageSource = driver.getPageSource();
        Document doc = Jsoup.parse(pageSource);

        // Each review component has the unique class "a-section review aok-relative"
        doc.getElementsByClass("a-section review aok-relative").forEach(review -> {
          // Get the title of the review, the third child of the review-title class
          String title = review.getElementsByClass("review-title").select(":eq(2)").text();
          // Get the content of the review
          String body = review.getElementsByClass("review-text").text();
          // Get the alt text rating of the review
          String rating = review.getElementsByClass("a-icon-alt").text();
          // Check if the review is a verified purchase
          Boolean verifiedPurchase = review.getElementsByClass("review-data").text().contains("Verified Purchase");
          // Get the username of the reviewer
          String username = review.getElementsByClass("a-profile-name").text();
          // Get the date of the review
          String date = review.getElementsByClass("review-date").text();

          reviews.add(new Review(title, body, rating, verifiedPurchase, username, date));
        });

        System.out.println("The current page has been scraped, please navigate to the next page and press enter to continue or type 'exit' to stop scraping");
        String input = new Scanner(System.in).nextLine().trim();
        if (input.equals("exit")) {
          break;
        }
      }

      // Make a csv file with the reviews without dataColletr
      try {
        File file = new File("reviews.csv");
        FileWriter writer = new FileWriter(file);
        writer.write("Username,Date,Title,Body,Rating,Verified Purchase\n");

        for (Review review : reviews) {
            writer.write(review.getUsername() + "," + review.getDate() + "," + review.getTitle() + "," + review.getBody() + "," + review.getRating() + "," + review.getVerifiedPurchase() + "\n");
        }
        writer.close();
      } catch (Exception e) {
        e.printStackTrace();
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
