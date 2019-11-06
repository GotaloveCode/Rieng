package com.miriga.rieng.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.miriga.rieng.R
import com.miriga.rieng.data.gson.Choice
import com.miriga.rieng.data.gson.Quiz
import java.util.*

class ViewPagerAdapter(
    private val mContext: Context,
    private val questions: ArrayList<Quiz>,
    private val interaction: Interaction? = null
) : PagerAdapter() {
    private var mLayoutInflater: LayoutInflater? = null

    private var pos = 0

    interface Interaction {
        fun clicked(choice: Choice)
    }

    override fun getCount(): Int {
        return questions.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        pos = position
        mLayoutInflater =
            mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = mLayoutInflater!!.inflate(R.layout.fragment_step, null)
        val question = view.findViewById<TextView>(R.id.question)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val answerLayout = view.findViewById<ConstraintLayout>(R.id.answerlayout)
        val result  = view.findViewById<TextView>(R.id.result)
        val answer = view.findViewById<TextView>(R.id.answer)
        val icon = view.findViewById<AppCompatImageView>(R.id.icon)
        val answerAdapter = AnswerAdapter{
            toggleResultView(it,answerLayout,result,icon)
        }

        answerLayout.visibility = View.GONE
        recyclerView.adapter = answerAdapter
        val q = questions[position]
        question.text = q.question
        answer.text = q.quizzquestionchoices.first { a -> a.correct_answer != null }.answer
        answerAdapter.submitList(q.quizzquestionchoices)
        answerAdapter.notifyDataSetChanged()

        val viewPager = container as ViewPager
        viewPager.addView(view, 0)

        return view
    }

    private fun toggleResultView(ans: Choice, al:View, result:TextView, icon:AppCompatImageView) {
        al.visibility = View.VISIBLE
        var res = "InCorrect"
        if(ans.correct_answer == null){
            result.text = res
        }else{
            res = "Correct"
            result.text = res
        }
        icon.setImageResource(reserveDrawables(res))
        result.setTextColor(ContextCompat.getColor(result.context,getBg(res)))
        interaction?.clicked(ans)
    }

    private fun getBg(rsv: String?): Int {
        return when (rsv) {
            "Correct" -> R.color.green_60
            else -> R.color.red
        }
    }

    private fun reserveDrawables(rsv:String?):Int{
        return when (rsv) {
            "Correct" -> R.drawable.ic_check_circle
            else -> R.drawable.ic_block
        }
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val mViewPager = container as ViewPager
        val mView = `object` as View
        mViewPager.removeView(mView)
    }

}
