# PTT crawler

# 目的
對指定看板查詢特定資料，請且可以及時通知

# 初始建設(目前是手動)
aws EventBridge 網頁手創
---
aws dynamic db 基礎設定

table

Subscriber 訂閱人資訊
    name string (Kevin5603)
    notifyType number (1 - email/ 2 - sns)

Crawl 爬蟲會基於這張表來對特定資料進行查詢
    id number pk
    board string (Lifeismoney)
    keyword_condition_id List<number>
    subscriber_name string 

KeywordCondition 查詢條件設定
    id number pk
    type number(1 - author/ 2 - subject/ 3 - content/ 4 - push推文/ 5 - 噓文)
    keyword string (goshare/賣)
    crawl_id number

Article PTT文章
    id string (PTT文章代碼)
    author string
    title string
    content string
    link string
    created_date string

CrawlHistory 記錄以爬蟲的資料避免重複發送通知
    id number
    crawl_id number
    article_id number



-- 

# 技術
aws Lambda (Business logic)
aws s3 (Storage)
aws DynamicDB (Storage)
aws API Gateway (串接line API)
aws CloudWatch (log)
aws EventBridge (schedule)
aws SAM (CICD)
aws Cloudformation (CICD)
aws CodeBuild (CICD)
aws SQS (還沒做 - 目前預想是API gateway打到SQS -> lambda)


# 流程
使用EventBridge排程來觸發aws lambda(爬蟲程式)

# 實作目標
使用aws lambda 排程執行
- 使用爬蟲library抓特定網頁的資料
- 若有抓到特定的資料則發送通知，並且將"作者_標題_日期"當作key值記錄在資料庫中避免重複傳送
- 將資料存在dynamicDB中


lambda排程 (使用EventBridge 設定一分鐘爬一次)
1. 會DB中讀取board table資料，確認我們需要的爬蟲的看板 (ex Lifeismoney)
2. 對指定看板進行爬文並且確認該文章
3. 把資料(文章標題/作者/連結/內文/推文？)存入db中

# TODO list
1. 重構PTT爬文章 lambda logic
2. 重構查詢關鍵字 lambda logic
3. line lambda logic -- 剩下DB的邏輯處理(還沒決定好DB要怎麼做)

