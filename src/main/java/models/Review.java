package models;

/**
 * Created by Guest on 8/16/17.
 */
public class Review {

        private String content;
        private int rating;
        private int id;

        public Review(String content, int rating) {
            this.content = content;
            this.rating = rating;
        }

        public String getContent() {
            return content;
        }

        public int getId() {
            return id;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setId(int id) {
            this.id = id;
        }

    public int getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (id != review.id) return false;
        return content.equals(review.content);
    }

    @Override
    public int hashCode() {
        int result = content.hashCode();
        result = 31 * result + id;
        return result;
    }
}

