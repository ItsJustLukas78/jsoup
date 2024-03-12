import java.lang.reflect.Array;
import java.util.ArrayList;

// representation of amazon review
public class Review {
    private String title;
    private String body;
    private String rating;
    private Boolean verifiedPurchase;
    private String username;
    private String date;

    private double weight;

    private ArrayList<String> matchedTargetWords;

    public Review(String title, String body, String rating, Boolean verifiedPurchase, String username, String date) {
        this.title = title;
        this.body = body;
        this.rating = rating;
        this.verifiedPurchase = verifiedPurchase;
        this.username = username;
        this.date = date.substring(date.indexOf("on") + 3).replace(",", "");
        this.weight = 0;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getRating() {
        return rating;
    }

    public Boolean getVerifiedPurchase() {
        return verifiedPurchase;
    }

    public String getUsername() {
        return username;
    }
    public String getDate() {
        return date;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ArrayList<String> getMatchedTargetWords() {
        return matchedTargetWords;
    }

    public void setMatchedTargetWords(ArrayList<String> matchedTargetWords) {
        this.matchedTargetWords = matchedTargetWords;
    }

    public String toString() {
        return "Username: " + username + "\nDate: " + date + "\nTitle: " + title + "\nBody: " + body + "\nRating: " + rating + "\nVerified Purchase: " + verifiedPurchase + "\n";
    }
}
