package com.devhjs.loge

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testBottomNavigationTabs() {
        // 홈 화면 확인 (기본 시작)
        composeTestRule.onNodeWithText("홈").assertIsDisplayed()

        // 통계 탭 클릭
        composeTestRule.onNodeWithText("통계").performClick()
        composeTestRule.onNodeWithText("감정 점수 추이").assertIsDisplayed()

        // 설정 탭 클릭
        composeTestRule.onNodeWithText("설정").performClick()
        composeTestRule.onNodeWithText("프로필").assertIsDisplayed()
        
        // 다시 홈으로 복귀
        composeTestRule.onNodeWithText("홈").performClick()
        composeTestRule.onNodeWithText("Log.e").assertIsDisplayed()
    }
}
