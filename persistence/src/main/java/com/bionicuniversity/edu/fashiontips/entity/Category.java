package com.bionicuniversity.edu.fashiontips.entity;

/**
 * Enum Category represent categories of user's posts.
 * It can be Question about user's outfit,
 * or typically user post with some images
 */

public enum Category {
    QUESTION(CategoryType.QUESTION),
    POST(CategoryType.POST);

    private final String category;

    Category(String type) {
        this.category = type;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Category{" +
                "category='" + category + '\'' +
                '}';
    }

    public interface CategoryType {
        public static final String QUESTION = "QUESTION";
        public static final String POST = "POST";
    }
}
