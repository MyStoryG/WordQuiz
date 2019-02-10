package learn.kotlin.com.wordquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {
    private val MODE_WORD = 1
    private val MODE_COUNTRY = 2
    private val mQuestions = mutableListOf<Question>()
    private var mCurrentNumber: Int = 0
    private val mAnswer = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setQuestion()
        updateUi()
        setButton()
    }

    private fun setMode(): Int {
        return 0
    }

    private fun setQuestion() {
        val xml = applicationContext.resources.getXml(R.xml.words)
        lateinit var english: String
        lateinit var korean: String
        try {
            var eventType = xml.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (xml.name == "string") {
                        for (i in 0 until xml.attributeCount) {
                            if (xml.getAttributeName(i) == "english") {
                                english = xml.getAttributeValue(i)
                            } else if (xml.getAttributeName(i) == "korean") {
                                korean = xml.getAttributeValue(i)
                            }
                        }
                        mQuestions.add(Question(english, korean))
                    }
                }
                eventType = xml.next()
            }
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun showQuestion() {
        question_text.setText("${mCurrentNumber + 1}" + ". " + resources.getString(R.string.question_title))
        question_word_text.setText(mQuestions[mCurrentNumber].english)
    }

    private fun updateUi() {
        showQuestion()
        setAnswerData()
        setAnswerButtonText()
    }

    private fun setButton() {
        prev_button.setOnClickListener {
            mCurrentNumber = mCurrentNumber - 1
            if (mCurrentNumber < 0) {
                mCurrentNumber = mQuestions.size - 1
            }
            updateUi()
        }

        next_button.setOnClickListener {
            mCurrentNumber = (mCurrentNumber + 1) % mQuestions.size
            updateUi()
        }

        answer_one.setOnClickListener {
            if (mQuestions[mCurrentNumber].korean == mQuestions[mAnswer.get(0)].korean) {
                Toast.makeText(applicationContext, R.string.answer_true, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, R.string.answer_false, Toast.LENGTH_SHORT).show()
            }
        }

        answer_two.setOnClickListener {
            if (mQuestions[mCurrentNumber].korean == mQuestions[mAnswer.get(1)].korean) {
                Toast.makeText(applicationContext, R.string.answer_true, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, R.string.answer_false, Toast.LENGTH_SHORT).show()
            }
        }

        answer_three.setOnClickListener {
            if (mQuestions[mCurrentNumber].korean == mQuestions[mAnswer.get(2)].korean) {
                Toast.makeText(applicationContext, R.string.answer_true, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, R.string.answer_false, Toast.LENGTH_SHORT).show()
            }
        }

        answer_four.setOnClickListener {
            if (mQuestions[mCurrentNumber].korean == mQuestions[mAnswer.get(3)].korean) {
                Toast.makeText(applicationContext, R.string.answer_true, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext, R.string.answer_false, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setAnswerData() {
        var isDuplicated = false
        mAnswer.clear()
        mAnswer.add(0, -1)
        mAnswer.add(1, -1)
        mAnswer.add(2, -1)
        mAnswer.add(3, -1)

        var count = 0
        val random = Random()

        var temp: Int
        while (true) {
            temp = random.nextInt(mQuestions.size - 1)
            if (temp == mCurrentNumber) {
                continue
            }

            for (i in 0..2) {
                if (temp == mAnswer.get(i)) {
                    isDuplicated = true
                }
            }
            if (isDuplicated) {
                isDuplicated = false
                continue
            } else {
                mAnswer.set(count, temp)
                count++
            }
            if (count > 2) {
                break
            }
        }
        mAnswer.set(3, mCurrentNumber)
        Collections.shuffle(mAnswer)
    }

    private fun setAnswerButtonText() {
        answer_one.setText(mQuestions[mAnswer.get(0)].korean)
        answer_two.setText(mQuestions[mAnswer.get(1)].korean)
        answer_three.setText(mQuestions[mAnswer.get(2)].korean)
        answer_four.setText(mQuestions[mAnswer.get(3)].korean)
    }
}


