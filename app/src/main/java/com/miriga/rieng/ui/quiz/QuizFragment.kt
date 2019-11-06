package com.miriga.rieng.ui.quiz

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.miriga.rieng.R
import com.miriga.rieng.adapters.ViewPagerAdapter
import com.miriga.rieng.data.gson.Choice
import com.miriga.rieng.data.gson.Quiz
import com.miriga.rieng.databinding.FragmentQuizBinding
import com.miriga.rieng.ui.BaseFragment
import com.miriga.rieng.utils.InjectorUtils
import com.miriga.rieng.utils.showToast
import java.util.*


class QuizFragment : BaseFragment(), ViewPagerAdapter.Interaction {

    private val questions = ArrayList<Quiz>()
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var quizViewModel: QuizViewModel
    private lateinit var binding: FragmentQuizBinding
    private var quizId: Int = 0
    private var answerList = ArrayList<Pair<Int, Boolean>>()
    private lateinit var dialog: Dialog
    private var hasValue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = InjectorUtils.provideQuizViewModelFactory()
        quizViewModel = ViewModelProviders.of(this, factory).get(QuizViewModel::class.java)
        quizId = QuizFragmentArgs.fromBundle(arguments!!).id
        quizViewModel.fetchLevel(quizId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        binding.apply {
            viewModel = quizViewModel
            lifecycleOwner = viewLifecycleOwner
        }
        viewPagerAdapter = ViewPagerAdapter(requireContext(), questions, this)
        binding.pager.adapter = viewPagerAdapter
        binding.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                binding.msStepPrevButton.isEnabled = position != 0
                binding.msStepNextButton.isEnabled = (position != questions.count() - 1) && hasValue
            }
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
        binding.msStepPrevButton.setOnClickListener {
            val pageId = binding.pager.currentItem - 1
            binding.pager.currentItem = pageId
        }
        binding.msStepNextButton.setOnClickListener {
            val pageId = binding.pager.currentItem + 1
            binding.pager.currentItem = pageId
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        quizViewModel.getData().observe(viewLifecycleOwner, Observer { result ->
            questions.addAll(result)
            changeProgress(0)
            viewPagerAdapter.notifyDataSetChanged()
        })
        quizViewModel.getLoader().observe(viewLifecycleOwner, Observer { loading ->
            if (loading) {
                showProgressDialog()
            } else {
                hideProgressDialog()
            }
        })
        quizViewModel.getErrors().observe(viewLifecycleOwner, Observer {
            activity?.showToast(it, Toast.LENGTH_LONG)
        })
    }

    override fun clicked(choice: Choice) {
        val p: Pair<Int, Boolean> = if (choice.correct_answer != null) {
            Pair(choice.quizzquestion_id.toInt(), true)
        } else {
            Pair(choice.quizzquestion_id.toInt(), false)
        }
        addAnswers(p)
        hasValue = true
        val pageId = binding.pager.currentItem

        if (pageId == questions.size - 1) {
            val total = answerList.count()
            val score = answerList.filter { x -> x.second }.count()
            changeProgress(binding.pager.currentItem)
            showResultsDialog(score, total)
        } else {
            val handler = Handler()
            val runnable = Runnable {
                binding.pager.currentItem = pageId + 1
                changeProgress(pageId)
                hasValue = false
            }
            handler.postDelayed(runnable, 4000)
        }
    }

    private fun changeProgress(pageId: Int) {
        binding.msStepProgressBar.progress = ((pageId + 1) * 100) / questions.size
    }

    private fun addAnswers(p: Pair<Int, Boolean>) {
        if (answerList.map { a -> a.first }.contains(p.first)) {
            val index = answerList.map { a -> a.first }.indexOf(p.first)
            answerList[index] = p
        } else {
            answerList.add(p)
        }
    }

    private fun showResultsDialog(score: Int, total: Int) {
        dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
        dialog.setContentView(R.layout.dialog_results)
        dialog.setCancelable(true)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        val title = dialog.findViewById<View>(R.id.title) as TextView
        title.text = "You scored $score/$total"
        dialog.show()
        dialog.window!!.attributes = lp

        val handler = Handler()
        val runnable = Runnable {
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }
        dialog.setOnDismissListener {
            handler.removeCallbacks(
                runnable
            )
        }
        handler.postDelayed(runnable, 4000)
    }

}