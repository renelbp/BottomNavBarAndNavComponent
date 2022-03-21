package com.example.multimediaenandroidstudio

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.multimediaenandroidstudio.databinding.FragmentExternalStorageBinding
import java.io.*


class ExternalStorageFragment : Fragment() {
    private lateinit var binding: FragmentExternalStorageBinding

    private val filepath = "MyFileStorage"
    internal var myExternalFile: File?=null
    private val isExternalStorageReadOnly: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState
    }
    private val isExternalStorageAvailable: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == extStorageState
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExternalStorageBinding.inflate(inflater,container,false)


        binding.buttonSave.setOnClickListener{
            myExternalFile = File(requireContext().getExternalFilesDir(filepath), binding.editTextFile.text.toString())
            try {
                val fileOutPutStream = FileOutputStream(myExternalFile)
                fileOutPutStream.write(binding.editTextData.text.toString().toByteArray())
                fileOutPutStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Toast.makeText(requireContext(),"data save",Toast.LENGTH_SHORT).show()
            binding.editTextFile.text.clear()
            binding.editTextData.text.clear()
            hideKeyboardFrom(requireContext(),requireView())

        }

        binding.buttonView.setOnClickListener {
            myExternalFile = File(requireContext().getExternalFilesDir(filepath), binding.editTextFile.text.toString())

            val filename = binding.editTextFile.text.toString()
            myExternalFile = File(requireContext().getExternalFilesDir(filepath),filename)
            if(filename!=null && filename.trim()!=""){
                var fileInputStream = FileInputStream(myExternalFile)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while (run {
                        text = bufferedReader.readLine()
                        text
                    } != null) {
                    stringBuilder.append(text)
                }
                fileInputStream.close()
                //Displaying data on EditText
                Toast.makeText(requireContext(),stringBuilder.toString(),Toast.LENGTH_SHORT).show()
                binding.editTextData.setText(stringBuilder.toString()).toString()
            }else {
                Toast.makeText(requireContext(), "file name cannot be blank", Toast.LENGTH_LONG)
                    .show()
            }

            hideKeyboardFrom(requireContext(),requireView())

        }

        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            binding.buttonSave.isEnabled = false
        }

        return binding.root
    }
    fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}