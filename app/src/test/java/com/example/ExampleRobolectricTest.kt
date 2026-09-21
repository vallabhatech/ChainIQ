package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.viewmodel.ResiliGraphViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("ResiliGraph", appName)
  }

  @Test
  fun `verify initial incident and simulation flow`() {
    val vm = ResiliGraphViewModel()
    val incident = vm.incident.value
    assertEquals("Supplier A", incident.supplierName)
    assertEquals(15, incident.durationDays)
    assertEquals(82, vm.resilienceScore.value)

    // Test duration adjustment
    vm.setDurationDays(30)
    assertEquals(30, vm.durationDays.value)

    // Test priority selection
    vm.togglePriority("Low Carbon")
    assertTrue(vm.selectedPriorities.value.contains("Low Carbon"))

    // Test approval state
    vm.approvePlan()
    assertEquals("APPROVED", vm.approvalState.value)
    assertEquals(78, vm.resilienceScore.value)

    // Test demo scenario reset
    vm.loadDemoScenario()
    assertEquals(15, vm.durationDays.value)
    assertEquals("OPT-A", vm.selectedOptionId.value)
    assertEquals(82, vm.resilienceScore.value)
  }
}
