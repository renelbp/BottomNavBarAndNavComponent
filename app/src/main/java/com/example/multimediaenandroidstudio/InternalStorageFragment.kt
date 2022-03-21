package com.example.multimediaenandroidstudio

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.multimediaenandroidstudio.databinding.FragmentInternalStorageBinding
import java.io.*


class InternalStorageFragment : Fragment() {

    private lateinit var binding: FragmentInternalStorageBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentInternalStorageBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener {
            val file: String = binding.editFile.text.toString()
            val data: String = binding.editData.text.toString()
            val fileOutputStream: FileOutputStream
            try {
                fileOutputStream = requireActivity().openFileOutput(file, Context.MODE_PRIVATE)
                fileOutputStream.write(data.toByteArray())
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Toast.makeText(requireContext(), "data save", Toast.LENGTH_LONG).show()
            binding.editFile.text.clear()
            binding.editData.text.clear()

            hideKeyboardFrom(requireContext(),requireView())
        }

        binding.btnView.setOnClickListener {
            val filename = binding.editFile.text.toString()
            if (filename != null && filename.trim() != "") {
                var fileInputStream: FileInputStream? = null
                fileInputStream = requireActivity().openFileInput(filename)
                var inputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder = StringBuilder()
                var text: String?
                while (run {
                        text = bufferedReader.readLine()
                        text
                    } != null) {
                    stringBuilder.append(text)
                }
                //Displaying data on EditText
                binding.editData.setText(stringBuilder.toString()).toString()
            } else {
                Toast.makeText(requireContext(), "file name cannot be blank", Toast.LENGTH_LONG)
                    .show()
            }
            hideKeyboardFrom(requireContext(), requireView())
        }
// Inflate the layout for this fragment
        return binding.root

    }

    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}


