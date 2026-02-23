package com.devhjs.loge.presentation.component

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import timber.log.Timber

/**
 * ADMob 리워드 광고를 로드하고 표시하는 매니저 클래스.
 * Activity 컨텍스트가 필요하므로 Composable Root에서 remember로 보관.
 *
 * 테스트 광고 단위 ID: ca-app-pub-3940256099942544/5224354917
 * 실제 출시 시에는 ADMob 콘솔에서 발급받은 ID로 교체 필요.
 */
class RewardAdManager(private val activity: Activity) {

    // 테스트용 리워드 광고 단위 ID (출시 시 실제 ID로 교체 예정)
    private val adUnitId = "ca-app-pub-3940256099942544/5224354917"

    private var rewardedAd: RewardedAd? = null
    private var isLoading = false

    /** 광고를 미리 로드. 화면 진입 시 호출하여 광고 준비. */
    fun loadAd() {
        if (rewardedAd != null || isLoading) return // 이미 로드됐거나 로딩 중이면 재로드 불필요

        isLoading = true
        val adRequest = AdRequest.Builder().build()

        RewardedAd.load(
            activity.applicationContext,
            adUnitId,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    isLoading = false
                    Timber.d("리워드 광고 로드 성공")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    rewardedAd = null
                    isLoading = false
                    Timber.e("리워드 광고 로드 실패: ${error.message}")
                }
            }
        )
    }

    /**
     * 광고를 표시한다.
     * @param onRewarded 사용자가 광고를 끝까지 시청했을 때 호출 (보상 지급)
     * @param onFailed 광고 표시 실패 또는 로드되지 않은 경우 호출
     */
    fun showAd(
        onRewarded: () -> Unit,
        onFailed: () -> Unit,
    ) {
        val ad = rewardedAd
        if (ad == null) {
            Timber.w("광고가 아직 준비되지 않았습니다. 다시 로드 시도.")
            loadAd() // 로드 재시도
            onFailed()
            return
        }

        // 광고 전체화면 콜백 설정
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                // 광고 닫힘 → 다음 광고 미리 로드
                rewardedAd = null
                loadAd()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                Timber.e("광고 표시 실패: ${error.message}")
                rewardedAd = null
                onFailed()
            }
        }

        // 광고 표시
        ad.show(activity) { _ ->
            // 사용자가 보상 획득(광고 시청 완료)
            Timber.d("리워드 광고 시청 완료 — 보상 지급")
            onRewarded()
        }
    }
}
