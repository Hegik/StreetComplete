package de.westnordost.streetcomplete.quests.allotments_plot

import de.westnordost.streetcomplete.R
import de.westnordost.streetcomplete.data.osm.geometry.ElementGeometry
import de.westnordost.streetcomplete.data.osm.mapdata.Element
import de.westnordost.streetcomplete.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcomplete.data.osm.mapdata.filter
import de.westnordost.streetcomplete.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcomplete.data.quest.AndroidQuest
import de.westnordost.streetcomplete.data.quest.NoCountriesExcept
import de.westnordost.streetcomplete.data.user.achievements.EditTypeAchievement
import de.westnordost.streetcomplete.osm.Tags

sealed class PlotRefAnswer
data class PlotRef(val ref: String) : PlotRefAnswer()
object NoVisiblePlotRef : PlotRefAnswer()

class AddPlotRef : OsmFilterQuestType<PlotRefAnswer>(), AndroidQuest {

    override val elementFilter = """
        ways, nodes with
        allotments = plot
        and !ref and !noref
    """

    override val changesetComment = "Added Ref number to allotments plot"
    override val wikiLink = "Key:ref"
    override val icon = R.drawable.quest_housenumber
    override val achievements = listOf(EditTypeAchievement.CITIZEN)
    override val enabledInCountries = NoCountriesExcept(
        "BE", "DK", "DE", "FI", "FR", "GB", "LU", "NL", "NO", "AT", "PL", "SE", "CH", "SK"
    ) // only countries, that are part of Office International du Coin de Terre et des Jardins Familiaux

    override fun getTitle(tags: Map<String, String>) = R.string.quest_allotments_plot_ref

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter("nodes, ways with allotments = plot")

    override fun createForm() = AddPlotRefForm()

    override fun applyAnswerTo(answer: PlotRefAnswer, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        when (answer) {
            is PlotRef -> tags["ref"] = answer.ref
            is NoVisiblePlotRef -> tags["noref"] = "yes"
        }
    }
}
