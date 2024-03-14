import java.util.ArrayList;

/*
 * Problem 2 Sell My Pet Food
 */
public class TargetedAd {

    public static void main(String[] args) {
        createTargetedAd("Buy our plastic shuttles for your badminton games! They are durable, long-lasting, and affordable!", "targetWords.csv");
    }

    public static void createTargetedAd(String advertisementString, String targetWordsFile)
    {
        if (advertisementString.equals("")) {
            advertisementString = "Buy our plastic shuttles for your badminton games! They are durable, long-lasting, and affordable!";
        }

        DataCollector dc = new DataCollector();
        dc.setData("reviews.csv", targetWordsFile);

        ArrayList<Review> relavantReviews = new ArrayList<Review>();

        while (true) {
            Review review = dc.getNextReview();
            if (review == null) {
                break;
            }
            String body = review.getBody();
            String[] words = body.split(" ");
            ArrayList<String> targetWords = dc.getTargetWords();

            ArrayList<String> matchedTargetWords = new ArrayList<String>();
            double totalWeight = 0.0;

            for (String targetWord : targetWords) {
                for (String word : words) {
                    if (word.equals(targetWord)) {
                        totalWeight += dc.getWeight(word);
                        matchedTargetWords.add(word);
                    }
                }
            }

            review.setWeight(totalWeight);
            review.setMatchedTargetWords(matchedTargetWords);

            if (totalWeight > 0.0) {
                // check if reviewer username already present, remove duplicates
                boolean alreadyPresent = false;
                for (Review relavantReview : relavantReviews) {
                    if (relavantReview.getUsername().equals(review.getUsername())) {
                        alreadyPresent = true;
                        break;
                    }
                }

                if (!alreadyPresent) {
                    relavantReviews.add(review);
                }
            }
        }

        dc.createTargetMarketFile("targetMarket.csv", relavantReviews);
        dc.prepareAdvertisement("targetedAd.csv", relavantReviews, advertisementString);
        dc.printAdvertisements();
    }

}
