package com.tjeit.a201912_finaltest

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_lotto.*
import java.util.*
import kotlin.collections.ArrayList

class LottoActivity : BaseActivity() {

    var totalWinMoney = 0L // 0을 Long타입으로 대입 => 그냥 0은 Int로 간주되어서, 50억 같은 큰 금액 담지 못함
    var usedMoney = 0L // 사용금액

    val winLottoNumArr = ArrayList<Int>()
    var bonusNumber = 0 // 보너스번호를 담기 위한 변수
    val winLottoNumTextViewList = ArrayList<TextView>()
    val myLottoNumTextViewList = ArrayList<TextView>()


    val mHandler = Handler()
    var isNowBuyingLotto = false


    var firstRankCount = 0
    var secondRankCount = 0
    var thirdRankCount = 0
    var fourthRankCount = 0
    var fifthRankCount = 0
    var noRankCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lotto)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        buyOneLottoBtn.setOnClickListener {
            makeWinLottoNum()
            checkLottoRank()
        }

        buyAutoLottoBtn.setOnClickListener {

            if (!isNowBuyingLotto) {
                buyLottoLoop()
                isNowBuyingLotto = true
                buyAutoLottoBtn.text = "자동구매 일시 정지"
            }
            else {
                mHandler.removeCallbacks(buyingLottoRunnable)
                isNowBuyingLotto = false
                buyAutoLottoBtn.text = "자동구매 재개"
            }

        }

    }

    fun buyLottoLoop() {
        mHandler.post(buyingLottoRunnable)

    }

    val buyingLottoRunnable = object : Runnable {
        override fun run() {

            if (usedMoney < 10000000) {
                makeWinLottoNum()
                checkLottoRank()
                buyLottoLoop()
            }
            else {
                runOnUiThread {
                    Toast.makeText(mContext, "로또 구매를 종료합니다.", Toast.LENGTH_SHORT).show()
                }

            }

        }

    }

    fun checkLottoRank() {

        var correctCount = 0

        for (myNumTxt in myLottoNumTextViewList) {
            val num = myNumTxt.text.toString().toInt()

            Log.d("적혀있는숫자들", num.toString())

            for (winNum in winLottoNumArr) {

                if (num == winNum) {
                    correctCount++
                    break
                }

            }

        }



        if (correctCount == 6) {
            totalWinMoney += 5000000000
            firstRankCount++
        }
        else if (correctCount == 5) {

            var isSecondRank = false

            for (myNumTxt in myLottoNumTextViewList) {
                val myNumber = myNumTxt.text.toString().toInt()

                if (myNumber == bonusNumber) {
                    isSecondRank = true
                }
            }

            if (isSecondRank) {
                totalWinMoney += 50000000
                secondRankCount++

            }
            else {
                totalWinMoney += 1500000
                thirdRankCount++
            }

        }
        else if (correctCount == 4) {
            totalWinMoney += 50000
            fourthRankCount++
        }
        else if (correctCount == 3) {
//            totalWinMoney += 5000
            usedMoney -= 5000
            fifthRankCount++
        }
        else {
            noRankCount++
        }

        totalWinMoneyTxt.text = String.format("%,d 원", totalWinMoney)


        usedMoney += 1000
        usedMoneyTxt.text = String.format("%,d 원", usedMoney)


        firstRankCountTxt.text = "${firstRankCount} 회"
        secondRankCountTxt.text = "${secondRankCount} 회"
        thirdRankCountTxt.text = "${thirdRankCount} 회"
        fourthRankCountTxt.text = "${fourthRankCount} 회"
        fifthRankCountTxt.text = "${fifthRankCount} 회"
        noRankCountTxt.text = "${noRankCount} 회"

    }

    fun makeWinLottoNum() {

        winLottoNumArr.clear()
        bonusNumber = 0

        for (i in 0..5) {
            while (true) {
                val randomInt = Random().nextInt(45) + 1

                var isDuplOk = true

                for (num in winLottoNumArr) {
                    if (randomInt == num) {
                        isDuplOk = false
                        break
                    }
                }

                if (isDuplOk) {
                    winLottoNumArr.add(randomInt)
                    break
                }
            }
        }

        Collections.sort(winLottoNumArr)


        while (true) {

            val tempNum = Random().nextInt(45) + 1

            var isDuplOk = true

            for (winNum in winLottoNumArr) {
                if (tempNum == winNum) {
                    isDuplOk = false
                }
            }

            if (isDuplOk) {
                bonusNumber = tempNum

                bonusNumTxt.text = bonusNumber.toString()
                break
            }

        }


        for (i in 0..5) {
            val tempTextView = winLottoNumTextViewList.get(i)
            val winNum = winLottoNumArr.get(i)

            tempTextView.text = winNum.toString()
        }

    }

    override fun setValues() {

        winLottoNumTextViewList.add(lottoNumTxt01)
        winLottoNumTextViewList.add(lottoNumTxt02)
        winLottoNumTextViewList.add(lottoNumTxt03)
        winLottoNumTextViewList.add(lottoNumTxt04)
        winLottoNumTextViewList.add(lottoNumTxt05)
        winLottoNumTextViewList.add(lottoNumTxt06)

        myLottoNumTextViewList.add(myNumTxt01)
        myLottoNumTextViewList.add(myNumTxt02)
        myLottoNumTextViewList.add(myNumTxt03)
        myLottoNumTextViewList.add(myNumTxt04)
        myLottoNumTextViewList.add(myNumTxt05)
        myLottoNumTextViewList.add(myNumTxt06)

    }

}
