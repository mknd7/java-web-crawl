## Web Crawler in Java

This is a web crawler initially started as part of my university course project.

1. Create a `Crawler` object with a `seed` URL.
2. Start crawling by invoking the crawler object's `crawl()` method.
3. `crawl()` recursively discovers pages and stores them in the `/crawl` directory.
4. View the resulting crawl using the crawler object's `printCrawlMap()` method.

### Features

- Easy to use with modular components
- Can customize storage directory for crawled webpages
- Each `CrawledURL` stores its parent and children URLs (bidirectional data)
- Multiple `Crawler` objects can be used together to crawl different websites

### TODO

- Add tests wherever possible!
- Option to browse through crawled data interactively
- Use [jsoup](https://jsoup.org/) (parser) for fetching URLs
- Store metadata about every crawled URL page (`URLPage`)
    - Store crawl data/metadata results in a DB (MySQL or MongoDB)
    - Option to view data/metadata for a specific crawled URL
- Ability to work with multiple `Crawler`s
    - Aggregate results from multiple `Crawler`s
    - Filter out common results from multiple `Crawler`s
- Support for other content / file types (possibly store)
- Mechanisms to prevent crashes (eg. limit file size)
- Multithreaded crawling/download of child URLs
    - Test performance improvements
    - See if max depth can be increased to 5
- Make this into a CLI app (using [Picocli](https://picocli.info/))
