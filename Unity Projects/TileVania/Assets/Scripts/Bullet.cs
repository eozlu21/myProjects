using System;
using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;

public class Bullet : MonoBehaviour
{
    private Rigidbody2D myRigidbody2D;
    [SerializeField] private float bulletSpeed = 20f;
    private PlayerManager player;
    [SerializeField] private int enemyKillPoints = 1000;

    void Start()
    {
        myRigidbody2D = GetComponent<Rigidbody2D>();
        player = FindObjectOfType<PlayerManager>();
        myRigidbody2D.velocity = new Vector2(player.transform.localScale.x * bulletSpeed, 0f);
    }

    // Update is called once per frame
    void Update()
    {
    }

    private void OnTriggerEnter2D(Collider2D other)
    {
        if (other.gameObject.tag.Equals("Enemy") && !other.gameObject.GetComponent<EnemyMovement>().isDefeated)
        {
            other.gameObject.GetComponent<EnemyMovement>().isDefeated = true;
            FindObjectOfType<GameSession>().IncrementScore(enemyKillPoints);
            Destroy(other.gameObject);
        }

        Destroy(gameObject);
    }
    
}