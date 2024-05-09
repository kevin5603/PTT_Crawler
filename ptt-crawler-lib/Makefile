build-PttCrawlerLayer:
	@ mvn clean install -s settings.xml
	@ mkdir -p $(ARTIFACTS_DIR)/java/lib
	@ cp -r ./target/ptt-crawler-lib-1.0.jar $(ARTIFACTS_DIR)/java/lib/
	@ zip -r layer_content.zip $(ARTIFACTS_DIR)/java
