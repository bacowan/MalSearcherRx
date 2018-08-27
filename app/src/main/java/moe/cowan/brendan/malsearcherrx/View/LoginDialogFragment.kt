package moe.cowan.brendan.malsearcherrx.View

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import moe.cowan.brendan.malsearcherrx.R

class LoginDialogFragment : DialogFragment() {
    @Override
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setView(activity?.layoutInflater?.inflate(R.layout.login_dialog, null))
        return builder.create()
    }
}