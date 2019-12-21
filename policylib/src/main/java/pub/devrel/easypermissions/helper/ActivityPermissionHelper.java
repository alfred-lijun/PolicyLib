package pub.devrel.easypermissions.helper;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.db.policylib.PermissionPolicy;

import java.util.List;

import pub.devrel.easypermissions.RationaleDialogFragment;

/**
 * Permissions helper for {@link Activity}.
 */
class ActivityPermissionHelper extends PermissionHelper<Activity> {
    private static final String TAG = "ActPermissionHelper";

    public ActivityPermissionHelper(Activity host) {
        super(host);
    }

    @Override
    public void directRequestPermissions(int requestCode, @NonNull String... perms) {
        ActivityCompat.requestPermissions(getHost(), perms, requestCode);
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String perm) {
        return ActivityCompat.shouldShowRequestPermissionRationale(getHost(), perm);
    }

    @Override
    public Context getContext() {
        return getHost();
    }

    @Override
    public void showRequestPermissionRationale(@NonNull String rationale,
                                               @NonNull String positiveButton,
                                               @NonNull String negativeButton,
                                               @StyleRes int theme,
                                               int requestCode, List<PermissionPolicy> list,
                                               @NonNull String... perms) {
        FragmentManager fm = getHost().getFragmentManager();
        Toast.makeText(getContext(), "权限", Toast.LENGTH_SHORT).show();
        // Check if fragment is already showing
        Fragment fragment = fm.findFragmentByTag(RationaleDialogFragment.TAG);
        if (fragment instanceof RationaleDialogFragment) {
            Log.d(TAG, "Found existing fragment, not showing rationale.");
            return;
        }

        RationaleDialogFragment
                .newInstance(positiveButton, negativeButton, rationale, theme, requestCode, list, perms)
                .showAllowingStateLoss(fm, RationaleDialogFragment.TAG);
    }
}