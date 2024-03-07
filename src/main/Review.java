// representation of amazon review
public class Review {
    private String title;
    private String body;
    private String rating;
    private Boolean verifiedPurchase;
    private String username;
    private String date;

    public Review(String title, String body, String rating, Boolean verifiedPurchase, String username, String date) {
        this.title = title;
        this.body = body;
        this.rating = rating;
        this.verifiedPurchase = verifiedPurchase;
        this.username = username;
        this.date = date.substring(date.indexOf("on") + 3).replace(",", "");
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

    public String toString() {
        return "Username: " + username + "\nDate: " + date + "\nTitle: " + title + "\nBody: " + body + "\nRating: " + rating + "\nVerified Purchase: " + verifiedPurchase + "\n";
    }
}
