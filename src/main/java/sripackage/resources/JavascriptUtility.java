package sripackage.resources;



import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class JavascriptUtility {

	private WebDriver driver;
	private JavascriptExecutor js;

	public JavascriptUtility(WebDriver driver) {
		this.driver = driver;
		this.js = (JavascriptExecutor) driver;
		// cast once in constructor — reused for all methods
	}

	// 1. Scroll element into view
	// Use when: element exists but not in viewport
	// waitForWebElementToAppear fails due to scroll needed
	public void scrollIntoView(WebElement element) {
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		
	}

	// 2. JS Click — bypass normal Selenium click
	// Use when: element is obscured by overlay
	// normal click throws ElementClickInterceptedException
	// Angular animation blocks click timing
	public void jsClick(WebElement element) {
		js.executeScript("arguments[0].click();", element);
	}

	// 3. Highlight element with red border — for debugging failures
	// Use when: Extent report screenshot needs visual indicator
	// debugging flaky element interactions
	public void highlightElement(WebElement element) {
		js.executeScript("arguments[0].style.border='3px solid red'", element);
	}

	// 4. Highlight and then remove border — clean after debug
	public void highlightAndRestore(WebElement element) {
		// save original style
		String originalStyle = element.getAttribute("style");
		// apply red border
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "border: 3px solid red;");

		// restore after 500ms
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, originalStyle);
	}

	// 5. Set value in field via JS
	// Use when: sendKeys() does not work on Angular fields
	// field has autocomplete blocking input
	public void setValueByJS(WebElement element, String value) {
		js.executeScript("arguments[0].value=arguments[1];", element, value);
	}

	// ✅ 6. Scroll to top of page
	// Use when: navigating back to top after long scroll
	public void scrollToTop() {
		js.executeScript("window.scrollTo(0, 0);");
	}

	// 7. Scroll to bottom of page
	// Use when: lazy-loaded content at bottom of page
	public void scrollToBottom() {
		js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
	}

	// 8. Get page title via JS
	// Use when: driver.getTitle() returns empty for Angular apps
	public String getPageTitle() {
		return (String) js.executeScript("return document.title;");
	}

	// 9. Check if element is in viewport
	// Use when: asserting element is visible without scrolling
	public boolean isElementInViewport(WebElement element) {
		return (Boolean) js.executeScript("var rect = arguments[0].getBoundingClientRect();"
				+ "return (rect.top >= 0 && rect.bottom <= window.innerHeight);", element);
	}

	// ✅ Scroll to TOP within a component
	// Use when: table/div has scrolled down, need to reset to top
	// Example: after reading last row, scroll back to first row
	public void scrollToTopOfComponent(WebElement component) {
		js.executeScript("arguments[0].scrollTop = 0;", component);
	}

	// ✅ Scroll to BOTTOM within a component
	// Use when: lazy-loaded rows in table, last row not visible
	// Example: scroll to bottom of order list to see latest order
	public void scrollToBottomOfComponent(WebElement component) {
		js.executeScript("arguments[0].scrollTop = arguments[0].scrollHeight;", component);
	}

	// ✅ Scroll DOWN by specific pixels within a component
	// Use when: need to scroll down gradually, not jump to bottom
	// Example: scroll table down by 200px to reveal next set of rows
	public void scrollDownByPixels(WebElement component, int pixels) {
		js.executeScript("arguments[0].scrollTop += arguments[1];", component, pixels);
	}

	// ✅ Scroll UP by specific pixels within a component
	// Use when: need to scroll up gradually within a component
	// Example: scroll product list up by 150px
	public void scrollUpByPixels(WebElement component, int pixels) {
		js.executeScript("arguments[0].scrollTop -= arguments[1];", component, pixels);
	}

	// ✅ Scroll to LEFT within a component
	// Use when: table has horizontal overflow, columns cut off on left
	// Example: wide data table — scroll to first column
	public void scrollToLeftOfComponent(WebElement component) {
		js.executeScript("arguments[0].scrollLeft = 0;", component);
	}

	// ✅ Scroll to RIGHT within a component
	// Use when: table has horizontal overflow, last columns not visible
	// Example: scroll right to see last column in wide table
	public void scrollToRightOfComponent(WebElement component) {
		js.executeScript("arguments[0].scrollLeft = arguments[0].scrollWidth;", component);
	}

	// ✅ Scroll RIGHT by specific pixels within a component
	// Use when: need to scroll right gradually
	// Example: scroll table right by 300px to reveal next columns
	public void scrollRightByPixels(WebElement component, int pixels) {
		js.executeScript("arguments[0].scrollLeft += arguments[1];", component, pixels);
	}

	// ✅ Scroll LEFT by specific pixels within a component
	// Use when: need to scroll left gradually
	// Example: scroll back left by 300px
	public void scrollLeftByPixels(WebElement component, int pixels) {
		js.executeScript("arguments[0].scrollLeft -= arguments[1];", component, pixels);
	}

	// ✅ Scroll to a specific element WITHIN a component
	// Use when: need to find a specific row inside a scrollable table
	// Example: scroll inside order table until specific order row is visible
	public void scrollToElementWithinComponent(WebElement component, WebElement targetElement) {
     js.executeScript(
         "arguments[0].scrollTop = arguments[1].offsetTop;",
         component,
         targetElement
     );
 }
	//highlight 
	public static void highlightFailingElement(WebElement element, WebDriver driver) {
	    try {
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript(
	            "arguments[0].style.border='3px solid red';" +
	            "arguments[0].style.outline='2px solid red';" +
	            "arguments[0].style.boxShadow='0 0 6px red';",
	            element
	        );
	        Thread.sleep(300); // brief pause so screenshot captures the red border
	    } catch (Exception e) {
	        // silently skip — never let highlight failure break the report
	    }
	}

	
	/*
	 * ## JavaScript Properties Explained ``` scrollTop → vertical scroll position
	 * (pixels from top) scrollHeight → total scrollable height of element
	 * scrollLeft → horizontal scroll position (pixels from left) scrollWidth →
	 * total scrollable width of element offsetTop → distance of target element from
	 * parent top
	 * 
	 * scrollTop = 0 → jump to top scrollTop = scrollHeight → jump to bottom
	 * scrollTop += 200 → scroll down 200px from current position scrollTop -= 200 →
	 * scroll up 200px from current position
	 * 
	 * scrollLeft = 0 → jump to leftmost position scrollLeft = scrollWidth → jump to
	 * rightmost position scrollLeft += 300 → scroll right 300px scrollLeft -= 300 →
	 * scroll left 300px
	 */
}