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

                Title("General")

                TextCard(Modifier.fillMaxWidth().padding(0.dp, 2.dp), "Product Name", model.general.productName())
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextCard(Modifier.weight(1.0f, true), "GPU", model.general.codeName())
                    TextCard(Modifier.weight(1.0f, true), "Revision", model.general.revision())
                    TextCard(Modifier.weight(1.0f, true), "Vendor", model.general.vendor().vendorName())
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextCard(Modifier.weight(2.0f, true), "Architecture", model.general.architecture())
                    TextCard(Modifier.weight(1.0f, true), "Foundry", model.general.foundry())
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextCard(Modifier.weight(1.0f, true), "Process Node", model.general.processNode())
                    TextCard(Modifier.weight(1.0f, true), "Transistors", model.general.transistors().toString())
                    TextCard(Modifier.weight(1.0f, true), "Die Size", "${model.general.dieSizeSquare()} mm²")
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextCard(Modifier.weight(1.0f, true), "Release Date", model.general.releaseDateString())
                    TextCard(Modifier.weight(2.0f, true), "BIOS Version", model.general.biosVersion())
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextCard(Modifier.weight(1.0f, true), "Device ID", model.general.deviceId())
                    TextCard(Modifier.weight(1.0f, true), "Bus Interface", model.general.busInterface())
                }

                Divider(modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 8.dp, bottom = 4.dp), color = Color(230, 230, 230))
                Title("Compute")

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextCard(Modifier.weight(1.0f, true), model.general.vendor().terms().computeUnitTerm().shortPlural, model.compute.computeUnits().toString())
                    TextCard(Modifier.weight(1.0f, true), "ROPs", model.compute.rasterOperationUnits().toString())
                    TextCard(Modifier.weight(1.0f, true), "TMUs", model.compute.textureMappingUnits().toString())
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
                    TextCard(Modifier.weight(1.0f, true), model.general.vendor().terms().shaderUnitTerm().shortPlural, model.compute.unifiedShaderUnits().toString())
                    TextCard(Modifier.weight(1.0f, true), model.general.vendor().terms().raytracingUnitTerm().shortPlural, model.compute.raytracingUnits().toString())
                    TextCard(Modifier.weight(1.0f, true), model.general.vendor().terms().tensorUnitTerm().shortPlural, model.compute.tensorUnits().toString())
                }

                Divider(modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 8.dp, bottom = 4.dp), color = Color(230, 230, 230))
                Title("Memory")

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextCard(Modifier.weight(1.0f, true), "Size", "${model.memory.size()} bytes")
                    TextCard(Modifier.weight(1.0f, true), "Type", model.memory.type())
                    TextCard(Modifier.weight(1.0f, true), "Vendor", model.memory.vendor())
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextCard(Modifier.weight(1.0f, true), "Bus Width", "${model.memory.busWidth()}-bit")
                    TextCard(Modifier.weight(1.0f, true), "Bandwidth", "0 b/s")
                }

                Divider(modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 8.dp, bottom = 4.dp), color = Color(230, 230, 230))
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

                Divider(modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 8.dp, bottom = 4.dp), color = Color(230, 230, 230))
                Title("Driver")

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextCard(Modifier.weight(1.0f, true), "Version", model.driver.version())
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextCard(Modifier.weight(1.0f, true), "Date", model.driver.dateString())
                    TextCard(Modifier.weight(1.0f, true), "Signature", model.driver.signature())
                }
            }

        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            adapter = rememberScrollbarAdapter(stateVertical)
        )
    }
}