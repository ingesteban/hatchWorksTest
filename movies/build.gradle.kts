plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kover)
    alias(libs.plugins.ktlint)
}

ktlint {
    verbose.set(true)

    filter {
        exclude("**/generated/**")
    }
}

kover {
    reports {
        filters {
            excludes {
                classes(
                    "*.BuildConfig",
                    "*.R",
                    "*.R$*",
                    "dagger.hilt.*",
                    "hilt_aggregated_deps.*",
                    "*_Factory",
                    "*_HiltModules*",
                    "*_MembersInjector",
                    "*_Provide*",
                    "*ComposableSingletons$*",
                    "*HatchWorksTestApp_HiltComponents*",
                    "*HatchWorksTestApplication_HiltComponents*",
                )

                packages("dev.esteban.movies.di")
            }
        }

        verify {
            rule {
                minBound(80)
            }
        }
    }
}

android {
    namespace = "dev.esteban.movies"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":${libs.esteban.network.get().name}"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.viewmodel.ktx)
    implementation(libs.androidx.paging.common)

    // Retrofit
    implementation(libs.squareup.retrofit2)
    implementation(libs.kotlinx.serialization)

    // Dagger hilt
    implementation(libs.google.dagger.hilt.android)
    implementation(libs.androidx.paging.runtime.ktx)
    ksp(libs.google.dagger.hilt.compiler)
    ksp(libs.google.dagger.hilt.android.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
}
