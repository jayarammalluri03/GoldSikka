    package com.jayaram.goldsikka.ui.view

    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Toast
    import androidx.core.widget.addTextChangedListener
    import androidx.fragment.app.Fragment
    import androidx.fragment.app.viewModels
    import androidx.lifecycle.Lifecycle
    import androidx.lifecycle.lifecycleScope
    import androidx.lifecycle.repeatOnLifecycle
    import androidx.navigation.fragment.findNavController
    import androidx.navigation.fragment.navArgs
    import com.jayaram.goldsikka.databinding.AddEditFragmentBinding
    import com.jayaram.goldsikka.databinding.HomeFragmentBinding
    import com.jayaram.goldsikka.ui.viewmodels.AddEditViewModel
    import com.jayaram.goldsikka.ui.viewmodels.HomeViewModel
    import dagger.hilt.android.AndroidEntryPoint
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.launch
    import kotlin.getValue


    @AndroidEntryPoint
    class AddEditFragment: Fragment(){


        private var _binding: AddEditFragmentBinding? = null
        private val viewModel: AddEditViewModel by viewModels()
        private val args: AddEditFragmentArgs by navArgs()


        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            _binding = AddEditFragmentBinding.inflate(inflater, container, false)
            return _binding?.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            viewModel.setNoteId(args.noteID)
            _binding?.etTitle?.addTextChangedListener {
                viewModel.updateTitle(it.toString())
            }

            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    launch {
                        viewModel.showToast.collect { message ->
                            if (message) {
                                Toast.makeText(
                                    requireContext(),
                                    "Please enter valid data",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    launch {
                        viewModel.title.collect { message ->
                            _binding?.etTitle?.setText(message)
                            _binding?.etTitle?.setSelection(message.length)
                        }
                    }
                    launch {
                        viewModel.description.collect { message ->
                            _binding?.etDescription?.setText(message)
                            _binding?.etDescription?.setSelection(message.length)
                        }
                    }
                }
            }
            _binding?.etDescription?.addTextChangedListener {
                viewModel.updateDescription(it.toString())
            }

            _binding?.btnSave?.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.saveData().collect { success ->
                        if (success) {
                            findNavController().popBackStack()
                        }
                    }
                }
            }
        }
    }