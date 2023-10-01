import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

// LWJGL
val lwjglVersion = "3.3.1"

val lwjglNatives = Pair(
    System.getProperty("os.name")!!,
    System.getProperty("os.arch")!!
).let { (name, arch) ->
    when {
        arrayOf("Linux", "FreeBSD", "SunOS", "Unit").any { name.startsWith(it) } ->
            if (arrayOf("arm", "aarch64").any { arch.startsWith(it) }) "natives-linux${if (arch.contains("64") || arch.startsWith("armv8")) "-arm64" else "-arm32"}"
            else "natives-linux"
        arrayOf("Mac OS X", "Darwin").any { name.startsWith(it) } -> "natives-macos${if (arch.startsWith("aarch64")) "-arm64" else ""}"
        arrayOf("Windows").any { name.startsWith(it) } ->
            if (arch.contains("64")) "natives-windows${if (arch.startsWith("aarch64")) "-arm64" else ""}"
            else "natives-windows-x86"
        else -> throw Error("Unrecognized or unsupported platform. Please set \"lwjglNatives\" manually")
    }
}
// End LWJGL

plugins {
    kotlin("jvm") version "1.9.10"
    id("org.jetbrains.compose") version "1.5.2"
}

group = "com.aaronjyoder"
version = "0.1.1"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven {
        url = URI("https://maven.maxhenkel.de/repository/public")
    }
}

dependencies {

    implementation(compose.desktop.currentOs)

    implementation("com.github.oshi:oshi-core:6.1.5")
    implementation("de.bommel24.nvmlj:nvmlj:1.0.2")

    // LWJGL
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))

    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-cuda")
    implementation("org.lwjgl", "lwjgl-egl")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-llvm")
    implementation("org.lwjgl", "lwjgl-openal")
    implementation("org.lwjgl", "lwjgl-opencl")
    implementation("org.lwjgl", "lwjgl-opengl")
    implementation("org.lwjgl", "lwjgl-opengles")
    implementation("org.lwjgl", "lwjgl-openvr")
    implementation("org.lwjgl", "lwjgl-openxr")
    implementation("org.lwjgl", "lwjgl-rpmalloc")
    implementation("org.lwjgl", "lwjgl-shaderc")
    implementation("org.lwjgl", "lwjgl-spvc")
    implementation("org.lwjgl", "lwjgl-vma")
    implementation("org.lwjgl", "lwjgl-vulkan")
    runtimeOnly("org.lwjgl", "lwjgl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-llvm", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openal", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengles", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openvr", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openxr", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-rpmalloc", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-shaderc", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-spvc", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-vma", classifier = lwjglNatives)
    if (lwjglNatives == "natives-macos" || lwjglNatives == "natives-macos-arm64") runtimeOnly("org.lwjgl", "lwjgl-vulkan", classifier = lwjglNatives)
    // End LWJGL
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            includeAllModules = true
            packageName = "Ohai"
            packageVersion = "1.0.0"
            description = "Ohai"
            vendor = "Aaron J Yoder"

            val iconsRoot = project.file("src/main/resources")
            linux {
                iconFile.set(iconsRoot.resolve("icon/ohai_icon_linux.png"))
                menuGroup = "Ohai"
            }

            windows {
                iconFile.set(iconsRoot.resolve("icon/ohai_icon_windows.ico"))
                menuGroup = "Ohai"
                dirChooser = true
                perUserInstall = true
            }

        }
    }
}