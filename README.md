## Web Crawler in Java

This is a web crawler initially started as part of my university course project.

1. Create a `Crawler` object `c` with a `seed` URL.
2. Start crawling by invoking the `c.crawl()` method.
3. `c.crawl()` recursively discovers pages and downloads them to the `/crawl` directory.
4. View the resulting crawl using the `c.printCrawlMap()` method.

### Features

- Easy to use with modular components
- Can customize storage directory for crawled webpages
- Each `CrawledURL` stores its parent and children URLs (bidirectional links)
- Each `CrawledURL` stores associated metadata (e.g. number of outlinks)
- Multiple `Crawler` objects can be used together to crawl different websites

### TODO

- Add tests wherever possible!
- Option to browse through crawled data interactively, with the option to view metadata for a specific crawled URL
- Use [jsoup](https://jsoup.org/) (parser) for fetching URLs - check if it makes things easier
- Store crawl metadata results in a DB (MySQL or MongoDB)
- Ability to work with multiple `Crawler`s
    - Aggregate results from multiple `Crawler`s
    - Filter out common results from multiple `Crawler`s
- Support for other content / file types (and possibly store) - only HTML supported now
- Mechanisms to prevent crashes (eg. limit file size)
- Multithreaded crawling/download of child URLs
    - Test performance improvements
    - See if max depth can be increased to 5
- Make this into a CLI app (using [Picocli](https://picocli.info/))
