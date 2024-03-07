// import jsoup then use it to scrape google.com html page

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

class Main {

  public static void main(String[] args) {
    try {

      String url = "https://www.amazon.com/Yonex-Mavis-Nylon-Badminton-Shuttlecock/product-reviews/B01CB0VZM8/ref=cm_cr_dp_d_show_all_btm?ie=UTF8&reviewerType=all_reviews";

      WebDriver driver = new ChromeDriver();
      driver.get(url);

      // Amazon tends to utilize captcha puzzles, so we need to wait for the user to solve it before capturing the page source
      System.out.println("Press any key to continue...");
      System.in.read();

      ArrayList<Review> reviews = new ArrayList<Review>();

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

      for (Review review : reviews) {
          System.out.println(review);
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
