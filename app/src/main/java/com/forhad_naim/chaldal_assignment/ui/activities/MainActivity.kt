package com.forhad_naim.chaldal_assignment.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.forhad_naim.chaldal_assignment.AppApplication
import com.forhad_naim.chaldal_assignment.R
import com.forhad_naim.chaldal_assignment.view_model.AppViewModelFactory
import com.forhad_naim.chaldal_assignment.view_model.UserEngagementViewModel
import com.forhad_naim.domain.usecase.UserEngagementUseCase
import com.forhad_naim.domain.utils.DomainConstants
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.InputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var appViewModelFactory: AppViewModelFactory
    private val userEngagementViewModel: UserEngagementViewModel by lazy {
        ViewModelProvider(this, appViewModelFactory).get(UserEngagementViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initProperty()
        initViewModel()
        btnSearch.setOnClickListener {
            if (isRequirementOk()) {
                etFromDate.setError(null)
                etToDate.setError(null)
                searchOnUserData()
            }
        }
    }

    fun isRequirementOk(): Boolean {
        var isOk = true
        try {
            if (TextUtils.isEmpty(etToDate.text)) {
                isOk = false
                etToDate.setError(getString(R.string.required_field))
            }
            if (TextUtils.isEmpty(etFromDate.text)) {
                isOk = false
                etFromDate.setError(getString(R.string.required_field))
            }
            if (isOk) {
                var simpleDateFormat = SimpleDateFormat(DomainConstants.DATE_FORMAT)
                simpleDateFormat.parse(etToDate.text.toString())
                simpleDateFormat.parse(etFromDate.text.toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            isOk = false
            Toast.makeText(
                this,
                "Invalid date format", Toast.LENGTH_SHORT
            ).show()
        }
        return isOk
    }

    fun searchOnUserData() {
        try {
            btnSearch.isEnabled = false
            btnSearch.postDelayed({
                btnSearch.isEnabled = true
            }, 500)

            userEngagementViewModel?.requestUserListByEngageStatus(
                etFromDate.text.toString(),
                etToDate.text.toString(),
                if (rbActive.isChecked) DomainConstants.STATUS.ACTIVE
                else if (rbSuperActive.isChecked) DomainConstants.STATUS.SUPER_ACTIVE
                else DomainConstants.STATUS.BORED
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun initProperty() {
        AppApplication.appComponent.inject(this)
    }

    fun initViewModel() {
        consumeLiveData()
    }

    fun consumeLiveData() {
        userEngagementViewModel?.consumeUserListByEngageStatus()
            ?.observe(
                this,
                Observer {
                    tvUserList.setText(it.toString())
                })
    }
}