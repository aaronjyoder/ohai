package view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import model.gpu.GpuModel
import model.gpu.compute.GpuComputeModel
import model.gpu.driver.GpuDriverModel
import model.gpu.general.GpuGeneralModel
import model.gpu.memory.GpuMemoryModel
import util.graphics.vendor.MarketingTerms

@Composable
fun GpuView(model: GpuModel) { // TODO: Should be a selectable list of GPUs in your system eventually
    Box(modifier = Modifier.fillMaxSize().padding(4.dp)) {
        val stateVertical = rememberScrollState(0)
        Box(
            modifier = Modifier.fillMaxSize().verticalScroll(stateVertical).padding(end = 8.dp, bottom = 12.dp)
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().padding(4.dp).background(Color(240, 240, 240)).border(1.dp, Color(230, 230, 230), RoundedCornerShape(4.dp))
            ) {

                GeneralSection(model.general)

                Divider(modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 8.dp, bottom = 4.dp), color = Color(230, 230, 230))

                ComputeSection(model.compute, model.general.vendor().terms())

                Divider(modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 8.dp, bottom = 4.dp), color = Color(230, 230, 230))

                MemorySection(model.memory)

                Divider(modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 8.dp, bottom = 4.dp), color = Color(230, 230, 230))

                ClocksSection() // TODO: Make model for clocks

                Divider(modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 8.dp, bottom = 4.dp), color = Color(230, 230, 230))

                DriverSection(model.driver)

            }

        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(stateVertical)
        )
    }
}

@Composable
fun GeneralSection(model: GpuGeneralModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Title("General")

        TextCard(Modifier.fillMaxWidth().padding(0.dp, 2.dp), "Product Name", model.productName())
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextCard(Modifier.weight(1.0f, true), "GPU", model.codeName())
            TextCard(Modifier.weight(1.0f, true), "Revision", model.revision())
            TextCard(Modifier.weight(1.0f, true), "Vendor", model.vendor().vendorName())
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextCard(Modifier.weight(2.0f, true), "Architecture", model.architecture())
            TextCard(Modifier.weight(1.0f, true), "Foundry", model.foundry())
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextCard(Modifier.weight(1.0f, true), "Process Node", model.processNode())
            TextCard(Modifier.weight(1.0f, true), "Transistors", model.transistors().toString())
            TextCard(Modifier.weight(1.0f, true), "Die Size", "${model.dieSizeSquare()} mm²")
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextCard(Modifier.weight(1.0f, true), "Release Date", model.releaseDateString())
            TextCard(Modifier.weight(2.0f, true), "BIOS Version", model.biosVersion())
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextCard(Modifier.weight(1.0f, true), "Device ID", model.deviceId())
            TextCard(Modifier.weight(1.0f, true), "Bus Interface", model.busInterface())
        }
    }
}

@Composable
fun ComputeSection(model: GpuComputeModel, terms: MarketingTerms) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Title("Compute")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextCard(Modifier.weight(1.0f, true), terms.computeUnitTerm().shortPlural, model.computeUnits().toString())
            TextCard(Modifier.weight(1.0f, true), "ROPs", model.rasterOperationUnits().toString())
            TextCard(Modifier.weight(1.0f, true), "TMUs", model.textureMappingUnits().toString())
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextCard(Modifier.weight(1.0f, true), "Pixel Fillrate", "Unknown")
            TextCard(Modifier.weight(1.0f, true), "Texture Fillrate", "Unknown")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextCard(Modifier.weight(1.0f, true), terms.shaderUnitTerm().shortPlural, model.unifiedShaderUnits().toString())
            TextCard(Modifier.weight(1.0f, true), terms.raytracingUnitTerm().shortPlural, model.raytracingUnits().toString())
            TextCard(Modifier.weight(1.0f, true), terms.tensorUnitTerm().shortPlural, model.tensorUnits().toString())
        }
    }
}

@Composable
fun MemorySection(model: GpuMemoryModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Title("Memory")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextCard(Modifier.weight(1.0f, true), "Size", "${model.size()} bytes")
            TextCard(Modifier.weight(1.0f, true), "Type", model.type())
            TextCard(Modifier.weight(1.0f, true), "Vendor", model.vendor())
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextCard(Modifier.weight(1.0f, true), "Bus Width", "${model.busWidth()}-bit")
            TextCard(Modifier.weight(1.0f, true), "Bandwidth", "0 b/s")
        }
    }
}

@Composable
fun ClocksSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Title("Clocks")

        Subtitle("Current")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextCard(Modifier.weight(1.0f, true), "Clock Freq.", "0 Hz")
            TextCard(Modifier.weight(1.0f, true), "Boost Freq.", "0 Hz")
            TextCard(Modifier.weight(1.0f, true), "Memory Freq.", "0 Hz")
        }

        Spacer(modifier = Modifier.height(6.dp))

        Subtitle("Default")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextCard(Modifier.weight(1.0f, true), "Clock Freq.", "0 Hz")
            TextCard(Modifier.weight(1.0f, true), "Boost Freq.", "0 Hz")
            TextCard(Modifier.weight(1.0f, true), "Memory Freq.", "0 Hz")
        }
    }
}

@Composable
fun DriverSection(model: GpuDriverModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Title("Driver")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextCard(Modifier.weight(1.0f, true), "Version", model.version())
        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextCard(Modifier.weight(1.0f, true), "Date", model.dateString())
            TextCard(Modifier.weight(1.0f, true), "Signature", model.signature())
        }
    }
}