package petros.efthymiou.groovy


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.internal.matcher.DrawableMatcher.Companion.withDrawable
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class PlaylistFeature {
    //Le digo que el target es la siguiente actividad Main activity es el target
    val mActivityRule = ActivityTestRule(MainActivity::class.java)
        @Rule get

    //Prueba que revisa si el texto playlist se muestra en la pantalla
    @Test
    fun displayScreenTitle() {
        assertDisplayed("Playlists")
    }
    //Prueba que revisa si se muestra una lista
    @Test
    fun displaysListOfPlaylist() {
        //Detenemos un poco la ejecucion para poder esperar a que se llene
        Thread.sleep(4000)
        //Agsegura que el id si existe y se muestran 10 elementos en el
        assertRecyclerViewItemCount(R.id.playlist_list, 10)
        //En la vista con el id de nombre , que sea descendiente de la lista con el id
        //despues checamos que el texto sea igual a " " y tambien revisamos que si se vea
        onView(
            allOf(withId(R.id.playlist_name),
                isDescendantOfA(nthChildOf(withId(R.id.playlist_list),
                    0))))
                .check(matches(withText("Hard Rock Cafe")))
                .check(matches(isDisplayed()))
        //utilizamos el mismo  codigo pero ahora revisamos la categoria
        onView(allOf(withId(R.id.playlist_category), isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 0))))
                .check(matches(withText("rock")))
                .check(matches(isDisplayed()))
        //Ahora revisamos que una imagen se despliegue en la fila. utilizamos el comando withdrawable
        onView(allOf(withId(R.id.playlist_image), isDescendantOfA(nthChildOf(withId(R.id.playlist_list), 0))))
                .check(matches(withDrawable(R.mipmap.playlist)))
                .check(matches(isDisplayed()))

    }

    //Con esta funcion nos ayuda a pasarle un parentview y con ese parent estamos acceediendo un hijo N
    fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == view)
            }
        }
    }

}