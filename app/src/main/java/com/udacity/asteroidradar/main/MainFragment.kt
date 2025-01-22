package com.udacity.asteroidradar.main


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidData
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.databinding.FragmentDetailBinding
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.repository.NasaRepository

class MainFragment : Fragment() {


    lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModel.Factory(requireActivity().application)
        ).get(MainViewModel::class.java)
    }

    private val adapter = MainAdapter({ viewModel.asteroidOnClick(it) })

    private val observer: Observer<List<Asteroid>> = Observer {
        adapter.updateListAsteroidCurrent(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.asteroidRecycler.adapter = adapter
        binding.asteroidRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.viewModel = viewModel

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.asteroidList.observe(viewLifecycleOwner) {
            adapter.updateListAsteroidCurrent(it)
        }
        viewModel.pictureOfTheDay.observe(viewLifecycleOwner) {
            val picasso = Picasso.get()
            picasso.setIndicatorsEnabled(true)
            picasso.isLoggingEnabled = true
            picasso.load(it?.url).into(binding.activityMainImageOfTheDay)
            binding.activityMainImageOfTheDay.contentDescription = it?.title
        }
        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner) {
            if (it != null) {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.asteroidOnClicked()
            }
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_overflow_menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.show_week_asteroids -> {
                viewModel.asteroidFilterOnClick(AsteroidFilter.WEEK)
                true
            }
            R.id.show_today_asteroids -> {
               viewModel.asteroidFilterOnClick(AsteroidFilter.TODAY)
                true
                }
            R.id.show_all_saved_asteroids -> {
                viewModel.asteroidFilterOnClick(AsteroidFilter.ALL)
            true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
