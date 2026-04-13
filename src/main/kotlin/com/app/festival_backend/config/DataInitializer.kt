// commenting out this file to stop data Intializers so that by deafult on starting the app data is not created




//package com.app.festival_backend.config
//
//import com.app.festival_backend.entity.Category
//import com.app.festival_backend.entity.ImagePost
//import com.app.festival_backend.entity.VideoPost
//import com.app.festival_backend.repository.CategoryRepository
//import com.app.festival_backend.repository.ImagePostRepository
//import org.springframework.boot.CommandLineRunner
//import org.springframework.stereotype.Component
//import com.app.festival_backend.repository.VideoPostRepository
//import com.app.festival_backend.entity.QuotePost
//import com.app.festival_backend.repository.QuotePostRepository
//
//@Component
//class DataInitializer(
//    private val categoryRepository: CategoryRepository,
//    private val imagePostRepository: ImagePostRepository,
//    private val videoPostRepository: VideoPostRepository,
//    private val quotePostRepository: QuotePostRepository
//) : CommandLineRunner {
//
//    override fun run(vararg args: String?) {
//        val diwali = categoryRepository.findBySlug("diwali")
//            ?: categoryRepository.save(
//                Category(
//                    name = "Diwali",
//                    slug = "diwali",
//                    description = "Diwali wishes, quotes, and images",
//                    active = true,
//                    displayOrder = 1
//                )
//            )
//
//        val holi = categoryRepository.findBySlug("holi")
//            ?: categoryRepository.save(
//                Category(
//                    name = "Holi",
//                    slug = "holi",
//                    description = "Holi wishes, quotes, and images",
//                    active = true,
//                    displayOrder = 2
//                )
//            )
//
//        if (imagePostRepository.count() == 0L) {
//            imagePostRepository.saveAll(
//                listOf(
//                    ImagePost(
//                        title = "Diwali Image 1",
//                        imageUrl = "https://example.com/images/diwali1.jpg",
//                        thumbnailUrl = "https://example.com/images/diwali1-thumb.jpg",
//                        category = diwali,
//                        isPremium = false,
//                        active = true,
//                        displayOrder = 1
//                    ),
//                    ImagePost(
//                        title = "Holi Image 1",
//                        imageUrl = "https://example.com/images/holi1.jpg",
//                        thumbnailUrl = "https://example.com/images/holi1-thumb.jpg",
//                        category = holi,
//                        isPremium = false,
//                        active = true,
//                        displayOrder = 1
//                    )
//                )
//            )
//        }
//
//        if (videoPostRepository.count() == 0L) {
//            videoPostRepository.saveAll(
//                listOf(
//                    VideoPost(
//                        title = "Diwali Video 1",
//                        videoUrl = "https://example.com/videos/diwali1.mp4",
//                        thumbnailUrl = "https://example.com/images/diwali-video-thumb.jpg",
//                        category = diwali,
//                        isPremium = false,
//                        active = true,
//                        displayOrder = 1
//                    )
//                )
//            )
//        }
//
//        if (quotePostRepository.count() == 0L) {
//            quotePostRepository.saveAll(
//                listOf(
//                    QuotePost(
//                        title = "Diwali Quote 1",
//                        quoteText = "May your life shine as bright as the festival lights.",
//                        author = "Unknown",
//                        category = diwali,
//                        isPremium = false,
//                        active = true,
//                        displayOrder = 1
//                    ),
//                    QuotePost(
//                        title = "Holi Quote 1",
//                        quoteText = "Let the colors of joy fill your life with happiness.",
//                        author = "Unknown",
//                        category = holi,
//                        isPremium = false,
//                        active = true,
//                        displayOrder = 1
//                    )
//                )
//            )
//        }
//    }
//}