package de.westnordost.streetcomplete.quests.allotments_plot

import de.westnordost.streetcomplete.R
import de.westnordost.streetcomplete.data.osm.geometry.ElementGeometry
import de.westnordost.streetcomplete.data.osm.mapdata.Element
import de.westnordost.streetcomplete.data.osm.mapdata.MapDataWithGeometry
import de.westnordost.streetcomplete.data.osm.mapdata.filter
import de.westnordost.streetcomplete.data.osm.osmquests.OsmFilterQuestType
import de.westnordost.streetcomplete.data.quest.AndroidQuest
import de.westnordost.streetcomplete.data.user.achievements.EditTypeAchievement
import de.westnordost.streetcomplete.osm.Tags

class AddPlotRef : OsmFilterQuestType<String>(), AndroidQuest {

    override val elementFilter = """
        ways, nodes with
        allotments = plot
        and !ref
    """
    override val changesetComment = "Added Ref number to allotments plot"
    override val wikiLink = "Key:ref"
    override val icon = R.drawable.quest_housenumber //ICON ANFERTIGGEN
    override val achievements = listOf(EditTypeAchievement.CITIZEN)

    override fun getTitle(tags: Map<String, String>) = R.string.quest_allotments_plot_ref

    override fun getHighlightedElements(element: Element, getMapData: () -> MapDataWithGeometry) =
        getMapData().filter("nodes, ways with allotments = plot")

    override fun createForm() = AddPlotRefForm()

    override fun applyAnswerTo(answer: String, tags: Tags, geometry: ElementGeometry, timestampEdited: Long) {
        tags["ref"] = answer
    }
}
