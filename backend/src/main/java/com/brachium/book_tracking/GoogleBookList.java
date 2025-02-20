package com.brachium.book_tracking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleBookList {

    private int totalItems;
    private String kind;
    private LinkedList<GoogleBook> items;

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public LinkedList<GoogleBook> getItems() {
        return items;
    }

    public void setItems(LinkedList<GoogleBook> items) {
        this.items = items;
    }

    public static class GoogleBook {
        private String kind;
        private String id;
        private VolumeInfo volumeInfo;

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public VolumeInfo getVolumeInfo() {
            return volumeInfo;
        }

        public void setVolumeInfo(VolumeInfo volumeInfo) {
            this.volumeInfo = volumeInfo;
        }

        public static class VolumeInfo {
            private String title;
            private LinkedList<String> authors;
            private String publishedDate;
            private String publisher;
            private String description;
            private LinkedList<IndustryIdentifier> industryIdentifiers;
            private int pageCount;
            private LinkedList<String> categories;
            private ImageLinks imageLinks;
            private String language;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public LinkedList<String> getAuthors() {
                return authors;
            }

            public void setAuthors(LinkedList<String> authors) {
                this.authors = authors;
            }

            public String getPublisher() {
                return publisher;
            }

            public void setPublisher(String publisher) {
                this.publisher = publisher;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public LinkedList<IndustryIdentifier> getIndustryIdentifiers() {
                return industryIdentifiers;
            }

            public void setIndustryIdentifiers(LinkedList<IndustryIdentifier> industryIdentifiers) {
                this.industryIdentifiers = industryIdentifiers;
            }

            public int getPageCount() {
                return pageCount;
            }

            public void setPageCount(int pageCount) {
                this.pageCount = pageCount;
            }

            public LinkedList<String> getCategories() {
                return categories;
            }

            public void setCategories(LinkedList<String> categories) {
                this.categories = categories;
            }

            public String getLanguage() {
                return language;
            }

            public void setLanguage(String language) {
                this.language = language;
            }

            public String getPublishedDate() {
                return publishedDate;
            }

            public void setPublishedDate(String publishedDate) {
                this.publishedDate = publishedDate;
            }

            public ImageLinks getImageLinks() {
                return imageLinks;
            }

            public void setImageLinks(ImageLinks imageLinks) {
                this.imageLinks = imageLinks;
            }

            public static class IndustryIdentifier {
                private String type;
                private String identifier;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getIdentifier() {
                    return identifier;
                }

                public void setIdentifier(String identifier) {
                    this.identifier = identifier;
                }
            }

            public static class ImageLinks {
                private String smallThumbnail;
                private String thumbnail;

                public String getSmallThumbnail() {
                    return smallThumbnail;
                }

                public void setSmallThumbnail(String smallThumbnail) {
                    this.smallThumbnail = smallThumbnail;
                }

                public String getThumbnail() {
                    return thumbnail;
                }

                public void setThumbnail(String thumbnail) {
                    this.thumbnail = thumbnail;
                }
            }
        }

        Book convertToBook() {
            if (this.volumeInfo == null) {
                return null;
            }

            String isbn13 = "";
            String isbn10 = "";
            String author = "";
            String categories = "";
            String imageLink = "";

            if (this.volumeInfo.authors != null) {
                author = this.volumeInfo.authors.getFirst();
            }

            if (this.volumeInfo.categories != null) {
                categories = this.volumeInfo.categories.getFirst();
            }

            if (this.volumeInfo.imageLinks != null) {
                imageLink = this.volumeInfo.imageLinks.thumbnail;
            }

            for (int i = 0; this.volumeInfo.industryIdentifiers != null && i < this.volumeInfo.industryIdentifiers.size(); i++) {
                if(this.volumeInfo.industryIdentifiers.get(i).type.equals("ISBN_13")) {
                    isbn13 = this.volumeInfo.industryIdentifiers.get(i).identifier;
                }
                else if(this.volumeInfo.industryIdentifiers.get(i).type.equals("ISBN_10")) {
                    isbn10 = this.volumeInfo.industryIdentifiers.get(i).identifier;
                }
            }

            int publishedYear = 0;

            if (this.volumeInfo.publishedDate != null) {
                publishedYear = Integer.parseInt(this.volumeInfo.publishedDate.split("-")[0]);
            }

            return new Book(this.id,
                    this.volumeInfo.title,
                    author,
                    publishedYear,
                    this.volumeInfo.publisher,
                    this.volumeInfo.description,
                    isbn13,
                    isbn10,
                    this.volumeInfo.pageCount,
                    categories,
                    imageLink,
                    this.volumeInfo.language);
        }
    }

    List<Book> convertList() {
        if (this.items == null) {
            return new LinkedList<>();
        }
        List<Book> result = new LinkedList<>();
        for (GoogleBook a : this.items) {
            result.add(a.convertToBook());
        }
        return result;
    }
}
