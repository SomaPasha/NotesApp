package space.kuz.notesapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import space.kuz.notesapp.R
import space.kuz.notesapp.domain.Note
import space.kuz.notesapp.ui.MainActivity

class ListNoteFragmentKotlin : Fragment() {
    private  var controller: Controller? = null
    private lateinit var textViewHead: TextView
    private lateinit var toolbar: MaterialToolbar
    private lateinit var note: Note
    private val subscrite: Subscrite = object : Subscrite {
        override fun setNewMessage(note: Note) {
            setMessage(note)
        }
    }

    private fun setMessage(note: Note) {
        //   textViewHead = ((MainActivity)requireActivity()).findViewById(R.id.text_view_head);
        (requireActivity() as MainActivity).app.notesRepository.updateNote(note.id, note)
        (requireActivity() as MainActivity).adapter.setData(
                (requireActivity() as MainActivity).app.notesRepository.notes)
        // ((MainActivity)requireActivity()).initRecyclerView();
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        controller = if (context is Controller) {
            context
        } else {
            throw IllegalStateException("Activity must implement ListNoteFragment.Controller")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        retainInstance = true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_note_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // binding =FragmentM
        return inflater.inflate(R.layout.activity_note_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getController().subscribe(subscrite)
        val bundle = arguments
        if (bundle != null) {
            note = bundle.getParcelable(ARG_NOTE_LIST)!!
            saveNote()
        }
        initToolbar(view)
        controller!!.openListNote()
    }

    private fun saveNote() {
        if (note!!.id == 0) {
            (requireActivity() as MainActivity).createNoteActivity(note)
        } else {
            (requireActivity() as MainActivity).updateNoteActivity(note)
        }
        //((MainActivity)requireActivity()).initRecyclerView();
    }

    private fun initToolbar(view: View) {
        toolbar = view.findViewById<View>(R.id.note_list_toolbar) as MaterialToolbar
        (requireActivity() as MainActivity).setSupportActionBar(toolbar)
    }

    private fun getController(): Controller {
        return requireActivity() as Controller
    }

    interface Controller {
        fun openListNote()
        fun subscribe(subscrite: Subscrite?)
        fun unsubscribe(subscrite: Subscrite?)
    }

    interface Subscrite {
        fun setNewMessage(note: Note)
    }

    override fun onDestroyView() {
        getController().unsubscribe(subscrite)
        super.onDestroyView()
    }

    override fun onDestroy() {
        controller = null
        super.onDestroy()
    }

    companion object {
        private const val ARG_NOTE_LIST = "NOTE_LIST"
      //  fun newInstance(note: Note?): ListNoteFragment {

     //   }

        @JvmStatic
        fun newInstance(note: Note): Fragment {
            val fragment = ListNoteFragmentKotlin()
            val bundle = Bundle()
            bundle.putParcelable(ARG_NOTE_LIST, note)
            fragment.arguments = bundle
            return fragment
        }
    }
}