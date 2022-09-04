package com.udacity.asteroidradar.main

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.detail.DetailFragment

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this,MainViewModel.Factory(activity!!.application)).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel.getimage()
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val adapter=RecyclerAdapter(
            RecyclerAdapter.OnClickListener {
                Navigation.findNavController(binding.root).navigate(MainFragmentDirections.actionShowDetail(it))
            }
        )
        binding.asteroidRecycler.adapter=adapter

        viewModel.weel.observe(viewLifecycleOwner) {
            if (it) {
                binding.statusLoadingWheel.visibility = View.GONE
            } else binding.statusLoadingWheel.visibility = View.VISIBLE
        }

        viewModel.aste.observe(viewLifecycleOwner) {

            if (it.size!==0){
                adapter.alist = it
                viewModel.changeweel()
            }
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
