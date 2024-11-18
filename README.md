# 多語系資料字典處理系統

在全球化應用或資料庫中，常需要處理來自不同語言的資料，這個系統提供特定語系的排序與比較功能，允許開發人員根據不同區域設置（如繁體中文、英文、德文等）的語言規則來進行資料處理，解決由於各國語系排序和比較規則不同而導致的數據錯誤或不一致問題，讓各國語言的字串排序和比較盡量依照本地語言的習慣。可以應用在客製化功能選單、特定語系的公告搜尋與排序、多語言的文件搜尋排序等。

本系統以繁體功能處理為範例，確保繁體中文資料在操作中能依據設定要求保持正確性與一致性，從而各類應用的可靠性，亦能延伸應用在全球化與區域化系統。

## 內含功能

依據指定語言的規則來進行比較，例如，對於繁體中文會依照拼音或筆劃順序，而不是字母的順序。
設定比較強度，例如：區分大小寫、區分重音或筆畫等。例如：比較字母，可忽略大小寫、重音符號或筆畫數量；或區分重音符號，但不區分大小寫。

## 使用技術

- Spring Boot
- Java

## 使用之弱點掃描工具

Fortify SCA

## 授權方式

MIT

## 使用案例

行政院公共工程委員會
「公共工程雲端系統資訊服務案」之公共工程雲端系統

## 2.安裝指南
> 請確保您的環境中安裝有 Java 17 、 Maven 3 以上版本，

```bat
# 執行步驟
> git clone https://github.com/TsubasaRush/multilingual-data-dictionary-processing-system.git
> cd multilingual-data-dictionary-processing-system
> mvn clean package
> java -jar target/multilingual-data-dictionary-processing-system-0.0.1-SNAPSHOT.jar
```





