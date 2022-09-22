import time
from selenium import webdriver
from selenium.webdriver.common.by import By
import urllib.request

carInfoURL = []
driver = webdriver.Chrome()

for i in range(1,10):
    driver.get("http://www.encar.com/ev/ev_carsearchlist.do?carType=ev&searchType=model&TG.R=D#!%7B%22action%22%3A%22(And.Hidden.N._.CarType.A._.(C.GreenType.Y._.EvType.전기차.))\"%2C\"toggle\"%3A%7B%7D%2C\"layer\"%3A\"\"%2C\"sort\"%3A\"ModifiedDate\"%2C\"page\"%3A"
               + str(i) + "%2C\"limit\"%3A20%7D")
    driver.implicitly_wait(10)

    elem = driver.find_element(By.XPATH, "//div[@class='rySch_con']/div[@class='section list']"
                                         "/div[@class='part list']/table[@class='car_list']/tbody")

    time.sleep(4)  # 2초 대기
    tmp = elem.find_elements(By.TAG_NAME, "tr")

    for tr in tmp:
        target = tr.find_element(By.CLASS_NAME, "inf").find_element(By.TAG_NAME, "a").get_attribute("href")
        carInfoURL.append(target)
    # print(elem.tag_name)
#자동차 상세페이지 URL 따오는 작업 종료

for i in range(len(carInfoURL)):
    driver.get(carInfoURL[i])
    driver.implicitly_wait(10)

    time.sleep(3)
    for j in range(1,5):
        image_URL = driver.find_element(By.XPATH,
                                        "/html/body/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/img").get_attribute("src").split("_001")
        realURL = image_URL[0] + "_00" + str(j) + image_URL[1]
        #마우스 호버링이 안먹어서 직접 문자열 나눠서 해결

        print(realURL)
        urllib.request.urlretrieve(realURL, "./crawlingPic/" + str(i) + "_" + str(j) + ".jpg")
#상세 페이지에 대한 사진 저장작업 종료