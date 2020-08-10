package codes.mohitkv.portfolio.exceptions;

public class BlogPostNotFoundException extends RuntimeException{
    public BlogPostNotFoundException(String id){
        super("Blog Post with id "+id+" not found");
    }
}
