package space.kuz.notesapp.ui.fragment

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.appbar.MaterialToolbar
import space.kuz.notesapp.ui.fragment.ListNoteFragment
import android.os.Bundle
import android.view.*
import space.kuz.notesapp.R
import space.kuz.notesapp.ui.MainActivity
import space.kuz.notesapp.ui.fragment.EditNoteFragmentKotlin
import androidx.fragment.app.Fragment
import space.kuz.notesapp.domain.Note
import java.lang.IllegalStateException

class EditNoteFragmentKotlin : Fragment() {
    private lateinit var headEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit  var dataTextView: TextView
    private lateinit  var note: Note
    private lateinit  var toolbar: MaterialToolbar
    private var controller: Controller? = null
    private val noteNull = Note()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        controller =
           context as Controller

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_note_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        val bundle = arguments
        bundle?.let { putAndSetView(it) }
        initToolbar(view)
        controller!!.openEditNote()
    }

    private fun initToolbar(view: View) {
        toolbar = view.findViewById<View>(R.id.note_edit_toolbar) as MaterialToolbar
        (requireActivity() as MainActivity).setSupportActionBar(toolbar)
    }

    private fun putAndSetView(bundle: Bundle) {
        note = bundle.getParcelable(ARG_NOTE)!!
        headEditText.setText(note.head)
        descriptionEditText.setText(note.description)
        dataTextView.text = dataTextView.text.toString() + note.date
    }

    private fun initView(view: View) {
        headEditText = view.findViewById(R.id.head_edit_text)
        descriptionEditText = view.findViewById(R.id.description_edit_text)
        dataTextView = view.findViewById(R.id.data_text_view_create_note)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_note_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun getController(): Controller {
        return requireActivity() as Controller
    }

    interface Controller {
        fun openEditNote()
    }

    override fun onDestroy() {
        controller = null
        super.onDestroy()
    }

    companion  object {
        private const val ARG_NOTE = "NOTE"
        @JvmStatic
        fun newInstance(note: Note): Fragment {
            val fragment = EditNoteFragmentKotlin()
            val bundle = Bundle()
            bundle.putParcelable(ARG_NOTE, note)
            fragment.arguments = bundle
            return fragment
        }
    }
}