package de.westnordost.streetcomplete.quests.allotments_plot

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doAfterTextChanged
import de.westnordost.streetcomplete.R
import de.westnordost.streetcomplete.databinding.QuestNameSuggestionBinding
import de.westnordost.streetcomplete.quests.AbstractOsmQuestForm

class AddPlotRefForm : AbstractOsmQuestForm<String>() {

    override val contentLayoutResId = R.layout.quest_name_suggestion
    private val binding by contentViewBinding(QuestNameSuggestionBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val input = binding.root

        input.hint = getString(R.string.quest_allotments_plot_ref_hint)
        input.doAfterTextChanged { text ->
            checkIsFormComplete()
        }

        input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onClickOk()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    override fun onClickOk() {
        applyAnswer(binding.root.text.toString())
    }

    override fun isFormComplete(): Boolean = !binding.root.text.isNullOrBlank()
}
