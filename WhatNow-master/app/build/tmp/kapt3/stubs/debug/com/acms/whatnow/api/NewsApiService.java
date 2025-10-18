package com.acms.whatnow.api;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001J@\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0003\u0010\u0005\u001a\u00020\u00062\b\b\u0003\u0010\u0007\u001a\u00020\u00062\b\b\u0003\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\u00062\b\b\u0003\u0010\u000b\u001a\u00020\u0006H\'J@\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0003\u0010\r\u001a\u00020\u00062\b\b\u0003\u0010\u0007\u001a\u00020\u00062\b\b\u0003\u0010\b\u001a\u00020\t2\b\b\u0001\u0010\n\u001a\u00020\u00062\b\b\u0003\u0010\u000e\u001a\u00020\u0006H\'\u00a8\u0006\u000f"}, d2 = {"Lcom/acms/whatnow/api/NewsApiService;", "", "getNewsWithImages", "Lretrofit2/Call;", "Lcom/acms/whatnow/models/NewsResponse;", "query", "", "country", "pageSize", "", "apiKey", "includeImages", "getTopHeadlines", "category", "content", "app_debug"})
public abstract interface NewsApiService {
    
    @retrofit2.http.GET(value = "top-headlines")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.acms.whatnow.models.NewsResponse> getTopHeadlines(@retrofit2.http.Query(value = "category")
    @org.jetbrains.annotations.NotNull()
    java.lang.String category, @retrofit2.http.Query(value = "country")
    @org.jetbrains.annotations.NotNull()
    java.lang.String country, @retrofit2.http.Query(value = "max")
    int pageSize, @retrofit2.http.Query(value = "token")
    @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @retrofit2.http.Query(value = "in")
    @org.jetbrains.annotations.NotNull()
    java.lang.String content);
    
    @retrofit2.http.GET(value = "search")
    @org.jetbrains.annotations.NotNull()
    public abstract retrofit2.Call<com.acms.whatnow.models.NewsResponse> getNewsWithImages(@retrofit2.http.Query(value = "q")
    @org.jetbrains.annotations.NotNull()
    java.lang.String query, @retrofit2.http.Query(value = "country")
    @org.jetbrains.annotations.NotNull()
    java.lang.String country, @retrofit2.http.Query(value = "max")
    int pageSize, @retrofit2.http.Query(value = "token")
    @org.jetbrains.annotations.NotNull()
    java.lang.String apiKey, @retrofit2.http.Query(value = "image")
    @org.jetbrains.annotations.NotNull()
    java.lang.String includeImages);
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}