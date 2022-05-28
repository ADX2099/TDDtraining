package petros.efthymiou.groovy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import petros.efthymiou.groovy.playlist.PlayListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, PlayListFragment.newInstance())
                    .commit()
        }
    }
}