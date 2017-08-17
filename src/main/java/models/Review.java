package models;

/**
 * Created by Guest on 8/16/17.
 */
public class Review {

        private String content;
        private int rating;
        private int id;
        private int codeSchoolId;

        public Review(String content, int rating, int codeSchoolId) {
            this.content = content;
            this.rating = rating;
            this.codeSchoolId = codeSchoolId;
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

    public int getCodeSchoolId() {
        return codeSchoolId;
    }

    public void setCodeSchoolId(int codeSchoolId) {
        this.codeSchoolId = codeSchoolId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (rating != review.rating) return false;
        if (id != review.id) return false;
        if (codeSchoolId != review.codeSchoolId) return false;
        return content.equals(review.content);
    }

    @Override
    public int hashCode() {
        int result = content.hashCode();
        result = 31 * result + rating;
        result = 31 * result + id;
        result = 31 * result + codeSchoolId;
        return result;
    }
}

